package lt.jaroslav.minibank.account.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lt.jaroslav.minibank.account.application.port.AccountRepositoryPort;
import lt.jaroslav.minibank.account.domain.Account;
import lt.jaroslav.minibank.transaction.application.port.AccountQueryPort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements AccountRepositoryPort, AccountQueryPort {

  private final AccountJpaRepository accountJpaRepository;

  @Override
  public Account save(Account account) {
    return accountJpaRepository.save(account);
  }

  @Override
  public Optional<Account> findById(Long id) {
    return accountJpaRepository.findById(id);
  }

  @Override
  public Optional<Account> findByIdForUpdate(Long id) {
    return accountJpaRepository.findByIdForUpdate(id);
  }

  @Override
  public List<Account> findAll() {
    return accountJpaRepository.findAll();
  }
}
