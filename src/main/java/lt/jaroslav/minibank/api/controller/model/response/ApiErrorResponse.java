package lt.jaroslav.minibank.api.controller.model.response;

import java.time.Instant;

public record ApiErrorResponse(
    Instant timestamp,
    int status,
    String error,
    String message,
    String path
) {
}
