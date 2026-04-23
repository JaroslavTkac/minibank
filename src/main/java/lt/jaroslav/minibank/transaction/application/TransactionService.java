package lt.jaroslav.minibank.transaction.application;

import java.util.List;
import lt.jaroslav.minibank.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.jaroslav.minibank.transaction.application.dto.TransactionDto;
import lt.jaroslav.minibank.transaction.application.dto.TransactionTransferDto;
import lt.jaroslav.minibank.transaction.application.port.AccountQueryPort;
import lt.jaroslav.minibank.transaction.application.port.TransactionRepositoryPort;
import lt.jaroslav.minibank.transaction.domain.Transaction;
import lt.jaroslav.minibank.transaction.domain.TransactionStatus;
import lt.jaroslav.minibank.transaction.infrastructure.mapper.TransactionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

  private final TransactionRepositoryPort repository;
  private final AccountQueryPort accountQueryPort;
  private final TransactionMapper mapper;
  private final TransactionOutboxService transactionOutboxService;

  @Transactional
  public TransactionDto transfer(TransactionTransferDto request) {
    var transaction = mapper.toEntity(request);
    var debtor = accountQueryPort.findById(request.debtorAccountId())
        .orElseThrow(() -> new NotFoundException("Account not found with id: " + request.debtorAccountId()));
    var creditor = accountQueryPort.findById(request.creditorAccountId())
        .orElseThrow(() -> new NotFoundException("Account not found with id: " + request.creditorAccountId()));

    transaction.setDebtorAccount(debtor);
    transaction.setCreditorAccount(creditor);
    transaction.setStatus(TransactionStatus.PENDING);

    var savedTransaction = repository.save(transaction);
    transactionOutboxService.enqueueTransactionCreated(
        savedTransaction.getId(),
        debtor.getId(),
        creditor.getId(),
        savedTransaction.getAmount()
    );
    return mapper.toDto(savedTransaction);
  }

  public TransactionDto getTransaction(Long id) {
    return mapper.toDto(getTransactionById(id));
  }

  public Transaction getTransactionById(long id) {
    return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Transaction not found with id: " + id));
  }

  @Transactional
  public void markCompleted(long transactionId) {
    var transaction = getTransactionById(transactionId);
    transaction.setStatus(TransactionStatus.COMPLETED);
    repository.save(transaction);
  }

  @Transactional
  public void markFailed(long transactionId, String reason) {
    var transaction = getTransactionById(transactionId);
    transaction.setStatus(TransactionStatus.FAILED);
    repository.save(transaction);
    log.warn("Transaction {} marked as FAILED due to: {}", transactionId, reason);
  }

  public List<TransactionDto> getTransactions() {
    return mapper.toDtoList(repository.findAll());
  }
}
