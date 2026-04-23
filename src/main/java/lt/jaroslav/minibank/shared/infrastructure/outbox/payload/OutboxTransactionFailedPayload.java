package lt.jaroslav.minibank.shared.infrastructure.outbox.payload;

public record OutboxTransactionFailedPayload(
    Long transactionId,
    String reason
) {
}
