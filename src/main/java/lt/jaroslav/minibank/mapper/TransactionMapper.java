package lt.jaroslav.minibank.mapper;

import java.util.List;
import lt.jaroslav.minibank.model.dto.TransactionDto;
import lt.jaroslav.minibank.model.dto.TransactionTransferDto;
import lt.jaroslav.minibank.model.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "debtorAccount", ignore = true)
  @Mapping(target = "creditorAccount", ignore = true)
  Transaction toEntity(TransactionTransferDto request);

  @Mapping(target = "debtorAccountId", expression = "java(transaction.getDebtorAccount().getId())")
  @Mapping(target = "creditorAccountId", expression = "java(transaction.getCreditorAccount().getId())")
  @Mapping(target = "status", expression = "java(transaction.getStatus() != null ? transaction.getStatus().name() : null)")
  TransactionDto toDto(Transaction transaction);

  List<TransactionDto> toDtoList(List<Transaction> transactions);
}
