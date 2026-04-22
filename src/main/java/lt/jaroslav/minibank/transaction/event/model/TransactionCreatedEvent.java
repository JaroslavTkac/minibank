package lt.jaroslav.minibank.transaction.event.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TransactionCreatedEvent extends ApplicationEvent {

  private final long eventId;
  private final long transactionId;
  private final long debtorAccountId;
  private final long creditorAccountId;
  private final BigDecimal amount;
  private final LocalDateTime createdAt;

  public TransactionCreatedEvent(
      Object source,
      long eventId,
      long transactionId,
      long debtorAccountId,
      long creditorAccountId,
      BigDecimal amount
  ) {
    super(source);
    this.eventId = eventId;
    this.transactionId = transactionId;
    this.debtorAccountId = debtorAccountId;
    this.creditorAccountId = creditorAccountId;
    this.amount = amount;
    this.createdAt = LocalDateTime.now();
  }
}
