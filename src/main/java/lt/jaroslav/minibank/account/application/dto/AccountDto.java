package lt.jaroslav.minibank.account.application.dto;

import java.math.BigDecimal;

public record AccountDto(
    long id,
    String ownerName,
    BigDecimal balance
) {
}
