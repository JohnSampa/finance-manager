package br.com.samp.financemanager.dto.mapstruct;

import br.com.samp.financemanager.dto.response.EarningResponse;
import br.com.samp.financemanager.model.Earning;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EarningMapper {

    EarningResponse toResponse(Earning earning);
}
