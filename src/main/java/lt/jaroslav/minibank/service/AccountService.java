package lt.jaroslav.minibank.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.jaroslav.minibank.api.controller.model.request.AccountRequest;
import lt.jaroslav.minibank.api.controller.model.response.AccountResponse;
import lt.jaroslav.minibank.mapper.AccountMapper;
import lt.jaroslav.minibank.model.exception.NotFoundException;
import lt.jaroslav.minibank.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository repository;
  private final AccountMapper mapper;

  public AccountResponse createAccount(AccountRequest request) {
    var account = mapper.toEntity(request);
    repository.save(account);

    return mapper.toResponse(account);
  }

  public AccountResponse getAccount(Long id) {
    var account = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Account not found with id: " + id));
    return mapper.toResponse(account);
  }

  public List<AccountResponse> getAccounts() {
    return mapper.toResponseList(repository.findAll());
  }
}
