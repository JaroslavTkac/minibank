package lt.jaroslav.minibank.shared.config;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import lt.jaroslav.minibank.shared.api.model.response.ApiErrorResponse;
import lt.jaroslav.minibank.shared.exception.InsufficientAmountException;
import lt.jaroslav.minibank.shared.exception.NotFoundException;
import lt.jaroslav.minibank.shared.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHttpRequestHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleNotFound(NotFoundException ex, HttpServletRequest request) {
    return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
  }

  @ExceptionHandler({ InsufficientAmountException.class, ValidationException.class })
  public ResponseEntity<ApiErrorResponse> handleValidation(RuntimeException ex, HttpServletRequest request) {
    return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
  }

  private ResponseEntity<ApiErrorResponse> buildErrorResponse(
      HttpStatus status,
      String message,
      HttpServletRequest request
  ) {
    ApiErrorResponse response = new ApiErrorResponse(
        Instant.now(),
        status.value(),
        status.getReasonPhrase(),
        message,
        request.getRequestURI()
    );

    return ResponseEntity.status(status).body(response);
  }
}
