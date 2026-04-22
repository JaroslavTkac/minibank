package lt.jaroslav.minibank.transaction.application.port;

import java.util.List;
import java.util.Optional;
import lt.jaroslav.minibank.transaction.domain.Transaction;

public interface TransactionRepositoryPort {

  Transaction save(Transaction transaction);

  Optional<Transaction> findById(Long id);

  List<Transaction> findAll();
}
