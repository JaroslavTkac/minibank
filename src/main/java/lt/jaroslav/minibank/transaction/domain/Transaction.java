package lt.jaroslav.minibank.transaction.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import lt.jaroslav.minibank.account.domain.Account;
import lt.jaroslav.minibank.shared.domain.BaseEntity;
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
  @Enumerated(EnumType.ORDINAL)
  private TransactionStatus status;
}
