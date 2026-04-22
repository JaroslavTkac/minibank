package lt.jaroslav.minibank.model.dto;

import java.math.BigDecimal;

public record AccountCreateDto(
    String ownerName,
    BigDecimal balance
) {
}
