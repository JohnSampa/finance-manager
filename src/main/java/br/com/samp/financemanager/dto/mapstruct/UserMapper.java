package br.com.samp.financemanager.dto.mapstruct;

import br.com.samp.financemanager.dto.UserRequest;
import br.com.samp.financemanager.dto.UserResponse;
import br.com.samp.financemanager.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = AccountMapper.class)
public interface UserMapper {

    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponseList(List<User> users);

    @Mapping(target = "accounts",ignore = true)
    @Mapping(target = "id",ignore = true)
    User toEntity(UserRequest userRequest);
}
