package br.com.samp.financemanager.services;

import br.com.samp.financemanager.dto.AccountRequest;
import br.com.samp.financemanager.dto.AccountResponse;
import br.com.samp.financemanager.dto.mapstruct.AccountMapper;
import br.com.samp.financemanager.model.Account;
import br.com.samp.financemanager.model.User;
import br.com.samp.financemanager.repository.AccountRepository;
import br.com.samp.financemanager.repository.UserRepository;
import br.com.samp.financemanager.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    public List<AccountResponse> listUserAccounts(Long userId) {
        return accountMapper
                .toListAccountsResponse(accountRepository.findByHolderId(userId));
    }

    public AccountResponse findByUserAndAccountId(Long userId,Long accountId) {
        Account account = accountRepository.findByHolderIdAndId(userId, accountId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Account not found")
                );
        return accountMapper.toAccountResponse(account);
    }

    public AccountResponse save(Long id, AccountRequest accountRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with id " + id));

        Account account = accountMapper.toEntity(accountRequest);

        account.setHolder(user);
        account = accountRepository.save(account);

        return  accountMapper.toAccountResponse(account);
    }
}
