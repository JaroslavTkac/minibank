package lt.jaroslav.minibank.account.infrastructure.persistence;

import lt.jaroslav.minibank.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {
}
