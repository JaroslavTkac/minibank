package lt.jaroslav.minibank.transaction.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lt.jaroslav.minibank.shared.infrastructure.outbox.OutboxEvent;
import lt.jaroslav.minibank.shared.infrastructure.outbox.OutboxEventRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionOutboxService {

  public static final String AGGREGATE_TYPE_TRANSACTION = "TRANSACTION";
  public static final String EVENT_TYPE_TRANSACTION_CREATED = "TransactionCreated";
  public static final String EVENT_TYPE_TRANSACTION_COMPLETED = "TransactionCompleted";
  public static final String EVENT_TYPE_TRANSACTION_FAILED = "TransactionFailed";

  private final OutboxEventRepository outboxEventRepository;
  private final ObjectMapper objectMapper;

  public void enqueueTransactionCreated(
      long transactionId,
      long debtorAccountId,
      long creditorAccountId,
      BigDecimal amount
  ) {
    Map<String, Object> payload = new LinkedHashMap<>();
    payload.put("transactionId", transactionId);
    payload.put("debtorAccountId", debtorAccountId);
    payload.put("creditorAccountId", creditorAccountId);
    payload.put("amount", amount);

    enqueue(transactionId, EVENT_TYPE_TRANSACTION_CREATED, payload);
  }

  public void enqueueTransactionCompleted(long transactionId) {
    Map<String, Object> payload = new LinkedHashMap<>();
    payload.put("transactionId", transactionId);
    enqueue(transactionId, EVENT_TYPE_TRANSACTION_COMPLETED, payload);
  }

  public void enqueueTransactionFailed(long transactionId, String reason) {
    Map<String, Object> payload = new LinkedHashMap<>();
    payload.put("transactionId", transactionId);
    payload.put("reason", reason);
    enqueue(transactionId, EVENT_TYPE_TRANSACTION_FAILED, payload);
  }

  private void enqueue(long aggregateId, String eventType, Map<String, Object> payload) {
    var event = new OutboxEvent();
    event.setAggregateType(TransactionOutboxService.AGGREGATE_TYPE_TRANSACTION);
    event.setAggregateId(aggregateId);
    event.setEventType(eventType);
    event.setPayload(toJson(payload));
    outboxEventRepository.save(event);
  }

  private String toJson(Map<String, Object> payload) {
    try {
      return objectMapper.writeValueAsString(payload);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Failed to serialize outbox payload", e);
    }
  }
}
