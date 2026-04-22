package lt.jaroslav.minibank.account.infrastructure.persistence;

import java.util.Optional;
import jakarta.persistence.LockModeType;
import lt.jaroslav.minibank.account.domain.Account;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select a from Account a where a.id = :id")
  Optional<Account> findByIdForUpdate(@Param("id") Long id);
}
