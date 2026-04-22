package lt.jaroslav.minibank.transaction.event.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.jaroslav.minibank.transaction.application.NotificationService;
import lt.jaroslav.minibank.transaction.event.model.TransactionCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionCreationListener {

  private final NotificationService notificationService;

  @EventListener
  public void handleTransactionRegistration(TransactionCreatedEvent event) {
    log.info("Transaction {} has been created.", event.getTransactionId());
    notificationService.sendNotification(event.getTransactionId());
  }
}
