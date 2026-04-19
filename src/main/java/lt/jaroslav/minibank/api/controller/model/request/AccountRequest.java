package lt.jaroslav.minibank.api.controller.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record AccountRequest(
    @NotBlank
    String ownerName,
    @NotNull
    @Min(0)
    BigDecimal balance
) {
}
