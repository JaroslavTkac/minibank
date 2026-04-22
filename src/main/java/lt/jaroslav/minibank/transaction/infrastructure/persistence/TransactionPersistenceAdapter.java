package lt.jaroslav.minibank.transaction.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lt.jaroslav.minibank.transaction.application.port.TransactionRepositoryPort;
import lt.jaroslav.minibank.transaction.domain.Transaction;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TransactionPersistenceAdapter implements TransactionRepositoryPort {

  private final TransactionJpaRepository transactionJpaRepository;

  @Override
  public Transaction save(Transaction transaction) {
    return transactionJpaRepository.save(transaction);
  }

  @Override
  public Optional<Transaction> findById(Long id) {
    return transactionJpaRepository.findById(id);
  }

  @Override
  public List<Transaction> findAll() {
    return transactionJpaRepository.findAll();
  }
}
