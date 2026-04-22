package lt.jaroslav.minibank.account.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.jaroslav.minibank.account.application.dto.AccountCreateDto;
import lt.jaroslav.minibank.account.application.dto.AccountDto;
import lt.jaroslav.minibank.account.application.port.AccountRepositoryPort;
import lt.jaroslav.minibank.account.infrastructure.mapper.AccountMapper;
import lt.jaroslav.minibank.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepositoryPort repository;
  private final AccountMapper mapper;

  public AccountDto createAccount(AccountCreateDto request) {
    var account = mapper.toEntity(request);
    var savedAccount = repository.save(account);

    return mapper.toDto(savedAccount);
  }

  public AccountDto getAccount(Long id) {
    var account = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Account not found with id: " + id));
    return mapper.toDto(account);
  }

  public List<AccountDto> getAccounts() {
    return mapper.toDtoList(repository.findAll());
  }
}
