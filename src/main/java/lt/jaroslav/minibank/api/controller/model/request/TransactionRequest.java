package lt.jaroslav.minibank.api.controller.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TransactionRequest(
    @NotNull
    Long debtorAccountId,
    @NotNull
    Long creditorAccountId,
    @NotNull
    @Min(0)
    BigDecimal amount
) {
}
