package lt.jaroslav.minibank.notification.event.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.jaroslav.minibank.notification.application.NotificationService;
import lt.jaroslav.minibank.shared.infrastructure.inbox.ProcessedEventService;
import lt.jaroslav.minibank.transaction.event.model.TransactionCompletedEvent;
import lt.jaroslav.minibank.transaction.event.model.TransactionCreatedEvent;
import lt.jaroslav.minibank.transaction.event.model.TransactionFailedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionNotificationListener {

  private final NotificationService notificationService;
  private final ProcessedEventService processedEventService;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @EventListener
  public void handleTransactionCreation(TransactionCreatedEvent event) {
    if (!processedEventService.registerIfNew("notification-transaction-created", event.getEventId())) {
      log.info("Skipping duplicate TransactionCreatedEvent id={} for transaction={}",
          event.getEventId(), event.getTransactionId());
      return;
    }
    log.info("Transaction {} has been created.", event.getTransactionId());
    notificationService.sendDebtorNotification(event.getTransactionId());
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @EventListener
  public void handleTransactionCompleted(TransactionCompletedEvent event) {
    if (!processedEventService.registerIfNew("notification-transaction-completed", event.getEventId())) {
      log.info("Skipping duplicate TransactionCompletedEvent id={} for transaction={}",
          event.getEventId(), event.getTransactionId());
      return;
    }
    log.info("Transaction {} has been completed.", event.getTransactionId());
    notificationService.sendDebtorNotification(event.getTransactionId());
    notificationService.sendCreditorNotification(event.getTransactionId());
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @EventListener
  public void handleTransactionFailed(TransactionFailedEvent event) {
    if (!processedEventService.registerIfNew("notification-transaction-failed", event.getEventId())) {
      log.info("Skipping duplicate TransactionFailedEvent id={} for transaction={}",
          event.getEventId(), event.getTransactionId());
      return;
    }
    log.info("Transaction {} has been failed.", event.getTransactionId());
    notificationService.sendDebtorNotification(event.getTransactionId());
  }
}
