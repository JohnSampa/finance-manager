package br.com.samp.financemanager.dto.mapstruct;

import br.com.samp.financemanager.dto.UserRequest;
import br.com.samp.financemanager.dto.UserResponse;
import br.com.samp.financemanager.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AccountMapper.class)
public interface UserMapper {

    UserResponse toUserResponse(User user);

    User toEntity(UserRequest userRequest);
}
