package lt.jaroslav.minibank.transaction.infrastructure.outbox;

public enum OutboxEventStatus {
  NEW,
  PUBLISHED,
  FAILED
}
