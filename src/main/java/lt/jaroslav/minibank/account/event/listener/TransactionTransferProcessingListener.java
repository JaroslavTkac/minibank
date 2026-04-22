package lt.jaroslav.minibank.account.event.listener;
import lt.jaroslav.minibank.account.application.TransactionTransferProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.jaroslav.minibank.transaction.event.model.TransactionCreatedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionTransferProcessingListener {

  private final TransactionTransferProcessingService transactionTransferProcessingService;

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleTransactionCreated(TransactionCreatedEvent event) {
    log.debug("Routing transaction {} for account transfer processing", event.getTransactionId());
    transactionTransferProcessingService.process(event);
  }
}
