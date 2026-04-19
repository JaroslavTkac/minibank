package lt.jaroslav.minibank.mapper;

import java.util.List;
import lt.jaroslav.minibank.api.controller.model.request.AccountRequest;
import lt.jaroslav.minibank.api.controller.model.response.AccountResponse;
import lt.jaroslav.minibank.model.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

  @Mapping(target = "id", ignore = true)
  Account toEntity(AccountRequest request);

  AccountResponse toResponse(Account account);

  List<AccountResponse> toResponseList(List<Account> accounts);
}
