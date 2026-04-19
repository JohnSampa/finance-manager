package br.com.samp.financemanager.dto.mapstruct;

import br.com.samp.financemanager.dto.request.UserRequest;
import br.com.samp.financemanager.dto.response.UserResponse;
import br.com.samp.financemanager.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = AccountMapper.class)
public interface UserMapper {

    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponseList(List<User> users);

    @Mapping(target = "uuid",ignore = true)
    @Mapping(target = "status",ignore = true)
    @Mapping(target = "accounts",ignore = true)
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "role",ignore = true)
    @Mapping(target = "address",ignore = true)
    @Mapping(target = "authorities",ignore = true)
    @Mapping(target = "expenses",ignore = true)
    @Mapping(target = "earnings",ignore = true)
    @Mapping(target = "failedLoginAttempts",ignore = true)
    @Mapping(target = "lockUntil",ignore = true)
    User toEntity(UserRequest userRequest);
}
