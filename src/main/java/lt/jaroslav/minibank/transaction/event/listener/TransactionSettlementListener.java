package lt.jaroslav.minibank.transaction.event.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.jaroslav.minibank.transaction.application.TransactionService;
import lt.jaroslav.minibank.transaction.event.model.TransactionCompletedEvent;
import lt.jaroslav.minibank.transaction.event.model.TransactionFailedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionSettlementListener {

  private final TransactionService transactionService;

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleCompleted(TransactionCompletedEvent event) {
    transactionService.markCompleted(event.getTransactionId());
    log.info("Transaction {} marked as COMPLETED", event.getTransactionId());
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleFailed(TransactionFailedEvent event) {
    transactionService.markFailed(event.getTransactionId(), event.getReason());
    log.info("Transaction {} marked as FAILED", event.getTransactionId());
  }
}
