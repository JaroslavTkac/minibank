package lt.jaroslav.minibank.shared.infrastructure.inbox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
    name = "PROCESSED_EVENT",
    uniqueConstraints = @UniqueConstraint(name = "uk_processed_event_consumer_event", columnNames = {"consumer", "event_id"})
)
public class ProcessedEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String consumer;

  @Column(name = "event_id", nullable = false)
  private Long eventId;

  @Column(nullable = false)
  private LocalDateTime processedAt;

  @PrePersist
  void prePersist() {
    if (processedAt == null) {
      processedAt = LocalDateTime.now();
    }
  }
}
