package lt.jaroslav.minibank.account.application.port;

import java.util.List;
import java.util.Optional;
import lt.jaroslav.minibank.account.domain.Account;

public interface AccountRepositoryPort {

  Account save(Account account);

  Optional<Account> findById(Long id);

  Optional<Account> findByIdForUpdate(Long id);

  List<Account> findAll();
}
