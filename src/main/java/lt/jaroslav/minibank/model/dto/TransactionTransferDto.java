package lt.jaroslav.minibank.model.dto;

import java.math.BigDecimal;

public record TransactionTransferDto(
    Long debtorAccountId,
    Long creditorAccountId,
    BigDecimal amount
) {
}
