package lt.jaroslav.minibank.shared.infrastructure.outbox.payload;

import java.math.BigDecimal;

public record OutboxTransactionCreatedPayload(
    Long transactionId,
    Long debtorAccountId,
    Long creditorAccountId,
    BigDecimal amount
) {
}
