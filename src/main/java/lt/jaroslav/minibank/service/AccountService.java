package lt.jaroslav.minibank.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.jaroslav.minibank.mapper.AccountMapper;
import lt.jaroslav.minibank.model.dto.AccountCreateDto;
import lt.jaroslav.minibank.model.dto.AccountDto;
import lt.jaroslav.minibank.model.exception.NotFoundException;
import lt.jaroslav.minibank.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository repository;
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
