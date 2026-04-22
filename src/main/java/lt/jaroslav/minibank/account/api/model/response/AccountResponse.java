package lt.jaroslav.minibank.account.api.model.response;

import java.math.BigDecimal;

public record AccountResponse(
    long id,
    String ownerName,
    BigDecimal balance
) {
}
