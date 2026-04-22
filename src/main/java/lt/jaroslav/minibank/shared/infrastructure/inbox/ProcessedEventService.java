package lt.jaroslav.minibank.shared.infrastructure.inbox;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessedEventService {

  private final ProcessedEventRepository processedEventRepository;

  public boolean registerIfNew(String consumer, long eventId) {
    var processedEvent = new ProcessedEvent();
    processedEvent.setConsumer(consumer);
    processedEvent.setEventId(eventId);

    try {
      processedEventRepository.save(processedEvent);
      return true;
    } catch (DataIntegrityViolationException ex) {
      return false;
    }
  }
}
