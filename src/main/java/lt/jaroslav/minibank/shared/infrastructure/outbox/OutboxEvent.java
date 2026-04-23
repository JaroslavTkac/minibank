package lt.jaroslav.minibank.shared.infrastructure.outbox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "OUTBOX_EVENT")
public class OutboxEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String aggregateType;

  @Column(nullable = false)
  private Long aggregateId;

  @Column(nullable = false)
  private String eventType;

  @Lob
  @Column(nullable = false)
  private String payload;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OutboxEventStatus status;

  @Column(nullable = false)
  private Integer attempts;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  private LocalDateTime publishedAt;

  @PrePersist
  void prePersist() {
    if (status == null) {
      status = OutboxEventStatus.NEW;
    }
    if (attempts == null) {
      attempts = 0;
    }
    if (createdAt == null) {
      createdAt = LocalDateTime.now();
    }
  }
}
