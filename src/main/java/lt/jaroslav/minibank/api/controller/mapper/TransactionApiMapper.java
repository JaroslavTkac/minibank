package lt.jaroslav.minibank.api.controller.mapper;

import java.util.List;
import lt.jaroslav.minibank.api.controller.model.request.TransactionRequest;
import lt.jaroslav.minibank.api.controller.model.response.TransactionResponse;
import lt.jaroslav.minibank.model.dto.TransactionDto;
import lt.jaroslav.minibank.model.dto.TransactionTransferDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionApiMapper {

  TransactionTransferDto toDto(TransactionRequest request);

  TransactionResponse toResponse(TransactionDto transactionDto);

  List<TransactionResponse> toResponseList(List<TransactionDto> transactionDtos);
}
