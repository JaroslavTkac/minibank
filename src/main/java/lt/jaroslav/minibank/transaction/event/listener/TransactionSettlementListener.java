package lt.jaroslav.minibank.transaction.event.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.jaroslav.minibank.shared.infrastructure.inbox.ProcessedEventService;
import lt.jaroslav.minibank.transaction.application.TransactionService;
import lt.jaroslav.minibank.transaction.event.model.TransactionCompletedEvent;
import lt.jaroslav.minibank.transaction.event.model.TransactionFailedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionSettlementListener {

  private final TransactionService transactionService;
  private final ProcessedEventService processedEventService;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleCompleted(TransactionCompletedEvent event) {
    if (!processedEventService.registerIfNew("transaction-settlement-completed", event.getEventId())) {
      log.info("Skipping duplicate TransactionCompletedEvent id={} for transaction={}",
          event.getEventId(), event.getTransactionId());
      return;
    }
    transactionService.markCompleted(event.getTransactionId());
    log.info("Transaction {} marked as COMPLETED", event.getTransactionId());
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleFailed(TransactionFailedEvent event) {
    if (!processedEventService.registerIfNew("transaction-settlement-failed", event.getEventId())) {
      log.info("Skipping duplicate TransactionFailedEvent id={} for transaction={}",
          event.getEventId(), event.getTransactionId());
      return;
    }
    transactionService.markFailed(event.getTransactionId(), event.getReason());
    log.info("Transaction {} marked as FAILED", event.getTransactionId());
  }
}
