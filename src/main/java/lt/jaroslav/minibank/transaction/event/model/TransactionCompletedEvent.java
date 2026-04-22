package lt.jaroslav.minibank.transaction.event.model;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TransactionCompletedEvent extends ApplicationEvent {

  private final long eventId;
  private final long transactionId;
  private final LocalDateTime createdAt;

  public TransactionCompletedEvent(Object source, long eventId, long transactionId) {
    super(source);
    this.eventId = eventId;
    this.transactionId = transactionId;
    this.createdAt = LocalDateTime.now();
  }
}
