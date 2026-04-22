package lt.jaroslav.minibank.transaction.application.port;

import java.util.Optional;
import lt.jaroslav.minibank.account.domain.Account;

public interface AccountQueryPort {

  Optional<Account> findById(Long id);
}
