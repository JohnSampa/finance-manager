package br.com.samp.financemanager.dto.mapstruct;

import br.com.samp.financemanager.dto.request.EarningRequest;
import br.com.samp.financemanager.dto.response.EarningResponse;
import br.com.samp.financemanager.model.Earning;
import br.com.samp.financemanager.model.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EarningMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "status", ignore = true)
    Earning toEntity(EarningRequest request);

    EarningResponse toResponse(Earning earning);

    List<EarningResponse> toResponseList(List<Earning> earnings);


}
