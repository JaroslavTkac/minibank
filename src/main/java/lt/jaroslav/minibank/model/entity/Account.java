package lt.jaroslav.minibank.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "ACCOUNT")
public class Account extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String ownerName;
  private BigDecimal balance;

  @OneToMany(mappedBy = "debtorAccount")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<Transaction> outgoingTransactions = new ArrayList<>();

  @OneToMany(mappedBy = "creditorAccount")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<Transaction> incomingTransactions = new ArrayList<>();
}
