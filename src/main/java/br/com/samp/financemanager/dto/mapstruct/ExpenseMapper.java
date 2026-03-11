package br.com.samp.financemanager.dto.mapstruct;

import br.com.samp.financemanager.dto.response.ExpenseResponse;
import br.com.samp.financemanager.model.Expense;
import br.com.samp.financemanager.model.enums.ExpenseStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.util.List;

import static br.com.samp.financemanager.model.enums.ExpenseStatus.PENDING_CONFIRMATION;
import static br.com.samp.financemanager.model.enums.ExpenseStatus.PLANNED;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(source = ".", target = "status", qualifiedByName = "calculateStatus")
    ExpenseResponse toExpenseResponse(Expense expense);

    List<ExpenseResponse> toExpenseResponseList(List<Expense> expenses);


    @Named("calculateStatus")
    default ExpenseStatus calculateStatus(Expense expense) {
        ExpenseStatus status = expense.getStatus();

        if(status != PLANNED){
            return status;
        }

        if (expense.getDate().isBefore(Instant.now())){
            return PENDING_CONFIRMATION;
        }

        return PLANNED;
    }
}
