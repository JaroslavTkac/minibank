package lt.jaroslav.minibank.transaction.api.mapper;

import java.util.List;
import lt.jaroslav.minibank.transaction.api.model.request.TransactionRequest;
import lt.jaroslav.minibank.transaction.api.model.response.TransactionResponse;
import lt.jaroslav.minibank.transaction.application.dto.TransactionDto;
import lt.jaroslav.minibank.transaction.application.dto.TransactionTransferDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionApiMapper {

  TransactionTransferDto toDto(TransactionRequest request);

  TransactionResponse toResponse(TransactionDto transactionDto);

  List<TransactionResponse> toResponseList(List<TransactionDto> transactionDtos);
}
