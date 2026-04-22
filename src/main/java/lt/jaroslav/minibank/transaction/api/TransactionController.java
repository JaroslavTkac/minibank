package lt.jaroslav.minibank.transaction.api;

import java.util.List;
import jakarta.validation.Valid;
import lt.jaroslav.minibank.transaction.api.mapper.TransactionApiMapper;
import lt.jaroslav.minibank.transaction.api.model.request.TransactionRequest;
import lt.jaroslav.minibank.transaction.api.model.response.TransactionResponse;
import lt.jaroslav.minibank.transaction.application.TransactionService;
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

  private final TransactionService service;
  private final TransactionApiMapper mapper;

  @PostMapping("/transfer")
  @ResponseStatus(HttpStatus.CREATED)
  public TransactionResponse transfer(@RequestBody @Valid TransactionRequest request) {
    return mapper.toResponse(service.transfer(mapper.toDto(request)));
  }

  @GetMapping("/{id}")
  public TransactionResponse getTransaction(@PathVariable Long id) {
    return mapper.toResponse(service.getTransaction(id));
  }

  @GetMapping
  public List<TransactionResponse> getTransactions() {
    return mapper.toResponseList(service.getTransactions());
  }
}
