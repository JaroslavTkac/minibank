package lt.jaroslav.minibank.transaction.infrastructure.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.jaroslav.minibank.transaction.event.model.TransactionCompletedEvent;
import lt.jaroslav.minibank.transaction.event.model.TransactionCreatedEvent;
import lt.jaroslav.minibank.transaction.event.model.TransactionFailedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "outbox.dispatcher.enabled", havingValue = "true", matchIfMissing = true)
public class OutboxEventDispatcher {

  private final OutboxEventRepository outboxEventRepository;
  private final ObjectMapper objectMapper;
  private final ApplicationEventPublisher publisher;

  @Value("${outbox.dispatcher.max-attempts:10}")
  private int maxAttempts;

  @Scheduled(fixedDelayString = "${outbox.dispatcher.fixed-delay-ms:1000}")
  @Transactional
  public void dispatchPendingEvents() {
    var pendingEvents = outboxEventRepository.findTop100ByStatusInAndAttemptsLessThanOrderByCreatedAtAsc(
        List.of(OutboxEventStatus.NEW, OutboxEventStatus.FAILED),
        maxAttempts
    );
    for (OutboxEvent event : pendingEvents) {
      try {
        dispatch(event);
        event.setStatus(OutboxEventStatus.PUBLISHED);
        event.setPublishedAt(LocalDateTime.now());
        outboxEventRepository.save(event);
      } catch (Exception ex) {
        event.setAttempts(event.getAttempts() + 1);
        event.setStatus(OutboxEventStatus.FAILED);
        outboxEventRepository.save(event);
        log.error("Failed to dispatch outbox event id={} type={}", event.getId(), event.getEventType(), ex);
      }
    }
  }

  private void dispatch(OutboxEvent event) {
    switch (event.getEventType()) {
      case TransactionOutboxService.EVENT_TYPE_TRANSACTION_CREATED -> dispatchTransactionCreated(event);
      case TransactionOutboxService.EVENT_TYPE_TRANSACTION_COMPLETED -> dispatchTransactionCompleted(event);
      case TransactionOutboxService.EVENT_TYPE_TRANSACTION_FAILED -> dispatchTransactionFailed(event);
      default -> throw new IllegalArgumentException("Unsupported eventType: " + event.getEventType());
    }
  }

  private void dispatchTransactionCreated(OutboxEvent event) {
    var payload = parsePayload(event.getPayload());
    publisher.publishEvent(new TransactionCreatedEvent(
        this,
        event.getId(),
        payload.transactionId(),
        payload.debtorAccountId(),
        payload.creditorAccountId(),
        payload.amount()
    ));
  }

  private void dispatchTransactionCompleted(OutboxEvent event) {
    var payload = parseCompletedPayload(event.getPayload());
    publisher.publishEvent(new TransactionCompletedEvent(this, event.getId(), payload.transactionId()));
  }

  private void dispatchTransactionFailed(OutboxEvent event) {
    var payload = parseFailedPayload(event.getPayload());
    publisher.publishEvent(new TransactionFailedEvent(this, event.getId(), payload.transactionId(), payload.reason()));
  }

  private OutboxTransactionCreatedPayload parsePayload(String payload) {
    try {
      return objectMapper.readValue(payload, OutboxTransactionCreatedPayload.class);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Failed to parse outbox payload", e);
    }
  }

  private OutboxTransactionCompletedPayload parseCompletedPayload(String payload) {
    try {
      return objectMapper.readValue(payload, OutboxTransactionCompletedPayload.class);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Failed to parse outbox completed payload", e);
    }
  }

  private OutboxTransactionFailedPayload parseFailedPayload(String payload) {
    try {
      return objectMapper.readValue(payload, OutboxTransactionFailedPayload.class);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Failed to parse outbox failed payload", e);
    }
  }
}
