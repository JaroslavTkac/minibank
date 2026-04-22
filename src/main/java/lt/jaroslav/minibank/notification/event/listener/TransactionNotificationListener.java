package lt.jaroslav.minibank.notification.event.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.jaroslav.minibank.notification.application.NotificationService;
import lt.jaroslav.minibank.transaction.event.model.TransactionCompletedEvent;
import lt.jaroslav.minibank.transaction.event.model.TransactionCreatedEvent;
import lt.jaroslav.minibank.transaction.event.model.TransactionFailedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionNotificationListener {

  private final NotificationService notificationService;

  @EventListener
  public void handleTransactionCreation(TransactionCreatedEvent event) {
    log.info("Transaction {} has been created.", event.getTransactionId());
    notificationService.sendDebtorNotification(event.getTransactionId());
  }

  @EventListener
  public void handleTransactionCompleted(TransactionCompletedEvent event) {
    log.info("Transaction {} has been completed.", event.getTransactionId());
    notificationService.sendDebtorNotification(event.getTransactionId());
    notificationService.sendCreditorNotification(event.getTransactionId());
  }

  @EventListener
  public void handleTransactionFailed(TransactionFailedEvent event) {
    log.info("Transaction {} has been failed.", event.getTransactionId());
    notificationService.sendDebtorNotification(event.getTransactionId());
  }
}
