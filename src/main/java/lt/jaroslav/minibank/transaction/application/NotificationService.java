package lt.jaroslav.minibank.transaction.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

  private final TransactionService transactionService;

  public void sendNotification(long transactionId) {
    var creditor = transactionService.getTransactionById(transactionId).getCreditorAccount();

    log.info("Sending fake notification to creditor [{}] identified by id: {}",
        creditor.getOwnerName(), creditor.getId());
    // pushing event to customer
  }
}
