package lt.jaroslav.minibank.api.controller.mapper;

import java.util.List;
import lt.jaroslav.minibank.api.controller.model.request.AccountRequest;
import lt.jaroslav.minibank.api.controller.model.response.AccountResponse;
import lt.jaroslav.minibank.model.dto.AccountCreateDto;
import lt.jaroslav.minibank.model.dto.AccountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountApiMapper {

  AccountCreateDto toDto(AccountRequest request);

  AccountResponse toResponse(AccountDto accountDto);

  List<AccountResponse> toResponseList(List<AccountDto> accountDtos);
}
