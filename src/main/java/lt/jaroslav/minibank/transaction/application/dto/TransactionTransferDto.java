package lt.jaroslav.minibank.transaction.application.dto;

import java.math.BigDecimal;

public record TransactionTransferDto(
    Long debtorAccountId,
    Long creditorAccountId,
    BigDecimal amount
) {
}
