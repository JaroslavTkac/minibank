package lt.jaroslav.minibank.event.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.jaroslav.minibank.event.model.TransactionCreatedEvent;
import lt.jaroslav.minibank.service.NotificationService;
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