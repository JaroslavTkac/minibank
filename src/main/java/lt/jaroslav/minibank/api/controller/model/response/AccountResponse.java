package lt.jaroslav.minibank.api.controller.model.response;

import java.math.BigDecimal;

public record AccountResponse(
    long id,
    String ownerName,
    BigDecimal balance
) {
}
