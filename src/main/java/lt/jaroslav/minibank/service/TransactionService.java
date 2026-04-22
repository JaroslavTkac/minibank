package lt.jaroslav.minibank.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.jaroslav.minibank.event.model.TransactionCreatedEvent;
import lt.jaroslav.minibank.mapper.TransactionMapper;
import lt.jaroslav.minibank.model.dto.TransactionDto;
import lt.jaroslav.minibank.model.dto.TransactionTransferDto;
import lt.jaroslav.minibank.model.entity.Transaction;
import lt.jaroslav.minibank.model.entity.TransactionStatus;
import lt.jaroslav.minibank.model.exception.NotFoundException;
import lt.jaroslav.minibank.repository.AccountRepository;
import lt.jaroslav.minibank.repository.TransactionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

  private final TransactionRepository repository;
  private final AccountRepository accountRepository;
  private final TransactionMapper mapper;
  private final ApplicationEventPublisher publisher;

  public TransactionDto transfer(TransactionTransferDto request) {
    var transaction = mapper.toEntity(request);
    var debtor = accountRepository.findById(request.debtorAccountId())
        .orElseThrow(() -> new NotFoundException("Account not found with id: " + request.debtorAccountId()));
    var creditor = accountRepository.findById(request.creditorAccountId())
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
