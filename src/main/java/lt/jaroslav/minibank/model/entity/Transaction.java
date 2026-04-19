package lt.jaroslav.minibank.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
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

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "debtor_account_id", nullable = false)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Account debtorAccount;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "creditor_account_id", nullable = false)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Account creditorAccount;

  private BigDecimal amount;
  private TransactionStatus status;
}
