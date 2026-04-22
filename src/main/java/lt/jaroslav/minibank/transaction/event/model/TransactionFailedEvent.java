package lt.jaroslav.minibank.transaction.event.model;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TransactionFailedEvent extends ApplicationEvent {

  private final long transactionId;
  private final String reason;
  private final LocalDateTime createdAt;

  public TransactionFailedEvent(Object source, long transactionId, String reason) {
    super(source);
    this.transactionId = transactionId;
    this.reason = reason;
    this.createdAt = LocalDateTime.now();
  }
}
