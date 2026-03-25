package br.com.samp.financemanager.dto.mapstruct;

import br.com.samp.financemanager.dto.request.EarningRequest;
import br.com.samp.financemanager.dto.response.EarningResponse;
import br.com.samp.financemanager.model.Earning;
import br.com.samp.financemanager.model.enums.TransactionStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.List;

import static br.com.samp.financemanager.model.enums.TransactionStatus.PENDING_CONFIRMATION;
import static br.com.samp.financemanager.model.enums.TransactionStatus.PLANNED;

@Mapper(componentModel = "spring",uses = CategoryMapper.class)
public interface EarningMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "status", ignore = true)
    Earning toEntity(EarningRequest request);

    @Mapping(source = ".",target = "status",qualifiedByName = "calculateStatus")
    EarningResponse toResponse(Earning earning);

    List<EarningResponse> toResponseList(List<Earning> earnings);


    @Named("calculateStatus")
    default TransactionStatus calculateStatus(Earning earning) {
        TransactionStatus status = earning.getStatus();

        if (status!= PLANNED) {
            return status;
        }

        if (earning.getDate().isBefore(LocalDate.now())){
            return PENDING_CONFIRMATION;
        }

        return PLANNED;
    }

}
