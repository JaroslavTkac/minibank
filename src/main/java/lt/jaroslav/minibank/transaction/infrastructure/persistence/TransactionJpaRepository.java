package lt.jaroslav.minibank.transaction.infrastructure.persistence;

import lt.jaroslav.minibank.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionJpaRepository extends JpaRepository<Transaction, Long> {
}
