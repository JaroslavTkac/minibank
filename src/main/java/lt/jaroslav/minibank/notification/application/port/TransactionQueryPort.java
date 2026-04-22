package lt.jaroslav.minibank.notification.application.port;

import java.util.Optional;
import lt.jaroslav.minibank.transaction.domain.Transaction;

public interface TransactionQueryPort {

  Optional<Transaction> findById(Long id);
}
