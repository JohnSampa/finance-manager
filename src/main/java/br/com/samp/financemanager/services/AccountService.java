package br.com.samp.financemanager.services;

import br.com.samp.financemanager.dto.mapstruct.AccountMapper;
import br.com.samp.financemanager.dto.request.AccountRequest;
import br.com.samp.financemanager.dto.response.AccountResponse;
import br.com.samp.financemanager.exceptions.BusinessException;
import br.com.samp.financemanager.exceptions.ResourceNotFoundException;
import br.com.samp.financemanager.infrastructure.security.service.AuthenticatedUserService;
import br.com.samp.financemanager.model.Account;
import br.com.samp.financemanager.model.User;
import br.com.samp.financemanager.repository.AccountRepository;
import br.com.samp.financemanager.repository.UserRepository;
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

    @Autowired
    private AuthenticatedUserService userAuthService;

    public List<AccountResponse> listAccounts() {
        User user = userAuthService.getAuthenticatedUser();

        return accountMapper
                .toListAccountsResponse(accountRepository.findByHolder(user));
    }

    public AccountResponse findById(Long id) {
        User user = userAuthService.getAuthenticatedUser();

        Account account = accountRepository.findByHolderAndId(user,id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Account not found")
                );
        return accountMapper.toAccountResponse(account);
    }

    public AccountResponse save(AccountRequest accountRequest) {
        User user = userAuthService.getAuthenticatedUser();

        Account account = accountMapper.toEntity(accountRequest);

        account.setHolder(user);
        account = accountRepository.save(account);

        return  accountMapper.toAccountResponse(account);
    }

    public void delete(Long id) {
        User user = userAuthService.getAuthenticatedUser();

        accountRepository.findByHolderAndId(user,id)
                .orElseThrow(()-> new ResourceNotFoundException("Account not found with id " + id));

        accountRepository.deleteByHolderAndId(user,id);
    }

    public AccountResponse deposit(Long accountId,Double amount) {
        User user = userAuthService.getAuthenticatedUser();

        Account account = accountRepository.findByHolderAndId(user, accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (amount < 0) throw new BusinessException("Amount cannot be negative");

        account.deposit(amount);

        return accountMapper.toAccountResponse(accountRepository.save(account));
    }

    public AccountResponse withdraw(Long accountId,Double amount) {
        User user = userAuthService.getAuthenticatedUser();

        Account account = accountRepository.findByHolderAndId(user, accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (amount < 0) throw new BusinessException("Amount cannot be negative");

        if (account.getBalance() < amount) throw new BusinessException("Insufficient balance");


        account.withdraw(amount);

        return accountMapper.toAccountResponse(accountRepository.save(account));
    }
}
