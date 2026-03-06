package br.com.samp.financemanager.dto.mapstruct;

import br.com.samp.financemanager.dto.AccountDTO;
import br.com.samp.financemanager.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDTO toAccountDTO(Account account);
}
