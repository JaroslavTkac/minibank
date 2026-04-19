package lt.jaroslav.minibank.api.controller;

import java.util.List;
import jakarta.validation.Valid;
import lt.jaroslav.minibank.api.controller.model.request.TransactionRequest;
import lt.jaroslav.minibank.api.controller.model.response.TransactionResponse;
import lt.jaroslav.minibank.service.TransactionService;
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
@RequestMapping("/transactions")
public class TransactionController {

  private final TransactionService transactionService;

  @PostMapping("/transfer")
  @ResponseStatus(HttpStatus.CREATED)
  public TransactionResponse transfer(@RequestBody @Valid TransactionRequest request) {
    return transactionService.transfer(request);
  }

  @GetMapping("/{id}")
  public TransactionResponse getTransaction(@PathVariable Long id) {
    return transactionService.getTransaction(id);
  }

  @GetMapping
  public List<TransactionResponse> getTransactions() {
    return transactionService.getTransactions();
  }
}
