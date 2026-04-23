package lt.jaroslav.minibank.shared.exception;

public class InsufficientAmountException extends RuntimeException {

  public InsufficientAmountException(String message) {
    super(message);
  }
}
