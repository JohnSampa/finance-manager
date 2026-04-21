package br.com.samp.financemanager.dto.mapstruct;

import br.com.samp.financemanager.dto.request.ExpenseRequest;
import br.com.samp.financemanager.dto.response.ExpenseResponse;
import br.com.samp.financemanager.model.Expense;
import br.com.samp.financemanager.model.enums.TransactionStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.List;

import static br.com.samp.financemanager.model.enums.TransactionStatus.PENDING_CONFIRMATION;
import static br.com.samp.financemanager.model.enums.TransactionStatus.PLANNED;

@Mapper(componentModel = "spring",uses = CategoryMapper.class)
public interface ExpenseMapper {

    @Mapping(target = "uuid",ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Expense toEntity(ExpenseRequest expenseRequest);

    @Mapping(source = ".", target = "status", qualifiedByName = "calculateStatus")
    ExpenseResponse toExpenseResponse(Expense expense);

    List<ExpenseResponse> toExpenseResponseList(List<Expense> expenses);


    @Named("calculateStatus")
    default TransactionStatus calculateStatus(Expense expense) {
        TransactionStatus status = expense.getStatus();

        if(status != PLANNED){
            return status;
        }

        if (expense.getDate().isBefore(LocalDate.now())){
            return PENDING_CONFIRMATION;
        }

        return PLANNED;
    }
}
