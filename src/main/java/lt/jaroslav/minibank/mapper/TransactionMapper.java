package lt.jaroslav.minibank.mapper;

import java.util.List;
import lt.jaroslav.minibank.api.controller.model.request.TransactionRequest;
import lt.jaroslav.minibank.api.controller.model.response.TransactionResponse;
import lt.jaroslav.minibank.model.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "status", ignore = true)
  Transaction toEntity(TransactionRequest request);

  @Mapping(target = "status", expression = "java(transaction.getStatus() != null ? transaction.getStatus().name() : null)")
  TransactionResponse toResponse(Transaction transaction);

  List<TransactionResponse> toResponseList(List<Transaction> transactions);
}
