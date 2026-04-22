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
import lt.jaroslav.minibank.transaction.event.model.TransactionCreatedEvent;
import lt.jaroslav.minibank.transaction.infrastructure.mapper.TransactionMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

  private final TransactionRepositoryPort repository;
  private final AccountQueryPort accountQueryPort;
  private final TransactionMapper mapper;
  private final ApplicationEventPublisher publisher;

  public TransactionDto transfer(TransactionTransferDto request) {
    var transaction = mapper.toEntity(request);
    var debtor = accountQueryPort.findById(request.debtorAccountId())
        .orElseThrow(() -> new NotFoundException("Account not found with id: " + request.debtorAccountId()));
    var creditor = accountQueryPort.findById(request.creditorAccountId())
        .orElseThrow(() -> new NotFoundException("Account not found with id: " + request.creditorAccountId()));

    transaction.setDebtorAccount(debtor);
    transaction.setCreditorAccount(creditor);
    transaction.setStatus(debtor.getBalance().compareTo(transaction.getAmount()) >= 0
        ? TransactionStatus.PENDING
        : TransactionStatus.FAILED);

    var savedTransaction = repository.save(transaction);
    publisher.publishEvent(new TransactionCreatedEvent(this, savedTransaction.getId()));
    return mapper.toDto(savedTransaction);
  }

  public TransactionDto getTransaction(Long id) {
    return mapper.toDto(getTransactionById(id));
  }

  public Transaction getTransactionById(long id) {
    return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Transaction not found with id: " + id));
  }

  public List<TransactionDto> getTransactions() {
    return mapper.toDtoList(repository.findAll());
  }
}
