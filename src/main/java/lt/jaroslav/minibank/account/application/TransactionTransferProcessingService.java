package lt.jaroslav.minibank.account.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.jaroslav.minibank.account.application.port.AccountRepositoryPort;
import lt.jaroslav.minibank.transaction.event.model.TransactionCompletedEvent;
import lt.jaroslav.minibank.transaction.event.model.TransactionCreatedEvent;
import lt.jaroslav.minibank.transaction.event.model.TransactionFailedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionTransferProcessingService {

  private final AccountRepositoryPort accountRepository;
  private final ApplicationEventPublisher publisher;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void process(TransactionCreatedEvent event) {
    if (event.getDebtorAccountId() == event.getCreditorAccountId()) {
      publisher.publishEvent(new TransactionFailedEvent(this, event.getTransactionId(), "Same account transfer"));
      return;
    }

    var firstLockId = Math.min(event.getDebtorAccountId(), event.getCreditorAccountId());
    var secondLockId = Math.max(event.getDebtorAccountId(), event.getCreditorAccountId());

    var firstAccount = accountRepository.findByIdForUpdate(firstLockId).orElse(null);
    var secondAccount = accountRepository.findByIdForUpdate(secondLockId).orElse(null);

    if (firstAccount == null || secondAccount == null) {
      publisher.publishEvent(new TransactionFailedEvent(this, event.getTransactionId(), "Account not found"));
      return;
    }

    var debtor = event.getDebtorAccountId() == firstLockId ? firstAccount : secondAccount;
    var creditor = event.getCreditorAccountId() == firstLockId ? firstAccount : secondAccount;

    if (debtor.getBalance().compareTo(event.getAmount()) < 0) {
      publisher.publishEvent(
          new TransactionFailedEvent(this, event.getTransactionId(), "Insufficient debtor balance")
      );
      return;
    }

    debtor.setBalance(debtor.getBalance().subtract(event.getAmount()));
    creditor.setBalance(creditor.getBalance().add(event.getAmount()));

    accountRepository.save(debtor);
    accountRepository.save(creditor);

    log.info("Processed transaction {}: debited account {}, credited account {}, amount {}",
        event.getTransactionId(), debtor.getId(), creditor.getId(), event.getAmount());
    publisher.publishEvent(new TransactionCompletedEvent(this, event.getTransactionId()));
  }
}
