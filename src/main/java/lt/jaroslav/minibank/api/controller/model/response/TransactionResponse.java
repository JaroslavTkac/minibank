package lt.jaroslav.minibank.api.controller.model.response;

import java.math.BigDecimal;

public record TransactionResponse(
    long id,
    long debtorAccountId,
    long creditorAccountId,
    BigDecimal amount,
    String status
) {
}
