package lt.jaroslav.minibank.shared.infrastructure.outbox;

public enum OutboxEventStatus {
  NEW,
  PUBLISHED,
  FAILED
}
