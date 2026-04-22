package lt.jaroslav.minibank.transaction.infrastructure.outbox;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

  List<OutboxEvent> findTop100ByStatusOrderByCreatedAtAsc(OutboxEventStatus status);

  List<OutboxEvent> findTop100ByStatusInAndAttemptsLessThanOrderByCreatedAtAsc(
      Collection<OutboxEventStatus> statuses,
      Integer maxAttempts
  );
}
