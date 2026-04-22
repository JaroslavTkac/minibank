package lt.jaroslav.minibank.transaction.infrastructure.outbox;

public record OutboxTransactionCompletedPayload(
    Long transactionId
) {
}
