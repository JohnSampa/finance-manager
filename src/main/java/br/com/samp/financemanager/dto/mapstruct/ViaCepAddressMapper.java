package br.com.samp.financemanager.dto.mapstruct;


import br.com.samp.financemanager.dto.ViaCepAddressDTO;
import br.com.samp.financemanager.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ViaCepAddressMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "number", ignore = true),
            @Mapping(target = "zipCode",source = "cep"),
            @Mapping(target = "street",source = "logradouro"),
            @Mapping(target = "neighborhood",source = "bairro"),
            @Mapping(target = "city",source = "localidade"),
            @Mapping(target = "state",source = "uf"),
            @Mapping(target = "users",ignore = true),
    })
    Address toEntity(ViaCepAddressDTO viaCepAddressDTO);
}
