package lt.jaroslav.minibank.api.controller;

import java.util.List;
import jakarta.validation.Valid;
import lt.jaroslav.minibank.api.controller.model.request.AccountRequest;
import lt.jaroslav.minibank.api.controller.model.response.AccountResponse;
import lt.jaroslav.minibank.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

  private final AccountService accountService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AccountResponse createAccount(@RequestBody @Valid AccountRequest request) {
    return accountService.createAccount(request);
  }

  @GetMapping("/{id}")
  public AccountResponse getAccount(@PathVariable Long id) {
    return accountService.getAccount(id);
  }

  @GetMapping
  public List<AccountResponse> getAccounts() {
    return accountService.getAccounts();
  }
}
