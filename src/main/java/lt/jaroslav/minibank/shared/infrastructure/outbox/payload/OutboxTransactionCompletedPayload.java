package lt.jaroslav.minibank.shared.infrastructure.outbox.payload;

public record OutboxTransactionCompletedPayload(
    Long transactionId
) {
}
