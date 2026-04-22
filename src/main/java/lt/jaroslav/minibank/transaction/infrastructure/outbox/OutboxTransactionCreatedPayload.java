package lt.jaroslav.minibank.transaction.infrastructure.outbox;

import java.math.BigDecimal;

public record OutboxTransactionCreatedPayload(
    Long transactionId,
    Long debtorAccountId,
    Long creditorAccountId,
    BigDecimal amount
) {
}
