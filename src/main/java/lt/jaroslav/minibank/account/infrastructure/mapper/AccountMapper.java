package lt.jaroslav.minibank.account.infrastructure.mapper;

import java.util.List;
import lt.jaroslav.minibank.account.application.dto.AccountCreateDto;
import lt.jaroslav.minibank.account.application.dto.AccountDto;
import lt.jaroslav.minibank.account.domain.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "outgoingTransactions", ignore = true)
  @Mapping(target = "incomingTransactions", ignore = true)
  Account toEntity(AccountCreateDto request);

  AccountDto toDto(Account account);

  List<AccountDto> toDtoList(List<Account> accounts);
}
