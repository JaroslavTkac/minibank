package lt.jaroslav.minibank.transaction.infrastructure.outbox;

public record OutboxTransactionFailedPayload(
    Long transactionId,
    String reason
) {
}
