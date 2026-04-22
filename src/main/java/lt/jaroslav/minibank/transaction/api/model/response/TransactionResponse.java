package lt.jaroslav.minibank.transaction.api.model.response;

import java.math.BigDecimal;

public record TransactionResponse(
    long id,
    long debtorAccountId,
    long creditorAccountId,
    BigDecimal amount,
    String status
) {
}
