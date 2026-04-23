package lt.jaroslav.minibank.shared.exception;

public class OutboxEventTypeException extends IllegalArgumentException {

  public OutboxEventTypeException(String message) {
    super(message);
  }
}
