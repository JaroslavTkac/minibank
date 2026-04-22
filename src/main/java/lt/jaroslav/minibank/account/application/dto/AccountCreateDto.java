package lt.jaroslav.minibank.account.application.dto;

import java.math.BigDecimal;

public record AccountCreateDto(
    String ownerName,
    BigDecimal balance
) {
}
