package lt.jaroslav.minibank.account.api.mapper;

import java.util.List;
import lt.jaroslav.minibank.account.api.model.request.AccountRequest;
import lt.jaroslav.minibank.account.api.model.response.AccountResponse;
import lt.jaroslav.minibank.account.application.dto.AccountCreateDto;
import lt.jaroslav.minibank.account.application.dto.AccountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountApiMapper {

  AccountCreateDto toDto(AccountRequest request);

  AccountResponse toResponse(AccountDto accountDto);

  List<AccountResponse> toResponseList(List<AccountDto> accountDtos);
}
