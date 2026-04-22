package lt.jaroslav.minibank.model.dto;

import java.math.BigDecimal;

public record TransactionDto(
    long id,
    long debtorAccountId,
    long creditorAccountId,
    BigDecimal amount,
    String status
) {
}
