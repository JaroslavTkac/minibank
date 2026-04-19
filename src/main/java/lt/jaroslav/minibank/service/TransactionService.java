package lt.jaroslav.minibank.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.jaroslav.minibank.api.controller.model.request.TransactionRequest;
import lt.jaroslav.minibank.api.controller.model.response.TransactionResponse;
import lt.jaroslav.minibank.mapper.TransactionMapper;
import lt.jaroslav.minibank.model.entity.TransactionStatus;
import lt.jaroslav.minibank.model.exception.NotFoundException;
import lt.jaroslav.minibank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

  private final TransactionRepository repository;
  private final TransactionMapper mapper;

  public TransactionResponse transfer(TransactionRequest request) {
    var transaction = mapper.toEntity(request);
    transaction.setStatus(TransactionStatus.PENDING);
    repository.save(transaction);

    return mapper.toResponse(transaction);
  }

  public TransactionResponse getTransaction(Long id) {
    var transaction = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Transaction not found with id: " + id));
    return mapper.toResponse(transaction);
  }

  public List<TransactionResponse> getTransactions() {
    return mapper.toResponseList(repository.findAll());
  }
}
