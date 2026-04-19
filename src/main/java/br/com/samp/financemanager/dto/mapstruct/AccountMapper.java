package br.com.samp.financemanager.dto.mapstruct;

import br.com.samp.financemanager.dto.request.AccountRequest;
import br.com.samp.financemanager.dto.response.AccountResponse;
import br.com.samp.financemanager.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse toAccountResponse(Account account);

    @Mapping(target = "uuid",ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "holder",ignore = true)
    Account toEntity(AccountRequest accountRequest);

    List<AccountResponse> toListAccountsResponse(List<Account> accounts);
}
