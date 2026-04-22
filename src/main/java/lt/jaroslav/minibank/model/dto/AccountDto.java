package lt.jaroslav.minibank.model.dto;

import java.math.BigDecimal;

public record AccountDto(
    long id,
    String ownerName,
    BigDecimal balance
) {
}
