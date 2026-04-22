package lt.jaroslav.minibank.notification.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.jaroslav.minibank.notification.application.port.TransactionQueryPort;
import lt.jaroslav.minibank.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

  private final TransactionQueryPort transactionQueryPort;

  public void sendNotification(long transactionId) {
    var creditor = transactionQueryPort.findById(transactionId)
        .orElseThrow(() -> new NotFoundException("Transaction not found with id: " + transactionId))
        .getCreditorAccount();

    log.info("Sending fake notification to creditor [{}] identified by id: {}",
        creditor.getOwnerName(), creditor.getId());
    // pushing event to customer
  }

}
