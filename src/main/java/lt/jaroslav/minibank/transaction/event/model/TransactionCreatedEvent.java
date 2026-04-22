package lt.jaroslav.minibank.transaction.event.model;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TransactionCreatedEvent extends ApplicationEvent {

  private final long transactionId;
  private final LocalDateTime createdAt;

  public TransactionCreatedEvent(Object source, long transactionId) {
    super(source);
    this.transactionId = transactionId;
    this.createdAt = LocalDateTime.now();
  }
}
