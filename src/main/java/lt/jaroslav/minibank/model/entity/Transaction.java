package lt.jaroslav.minibank.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.math.BigDecimal;

@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@Entity
@Table(name = "TRANSACTION")
public class Transaction extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long debtorAccountId;
  private Long creditorAccountId;
  private BigDecimal amount;
  private TransactionStatus status;
}
