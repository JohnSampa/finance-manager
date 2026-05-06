package br.com.samp.financemanager.services;

import br.com.samp.financemanager.dto.mapstruct.AccountMapper;
import br.com.samp.financemanager.dto.request.AccountRequest;
import br.com.samp.financemanager.dto.response.AccountResponse;
import br.com.samp.financemanager.exceptions.BusinessException;
import br.com.samp.financemanager.exceptions.DataBaseException;
import br.com.samp.financemanager.exceptions.ResourceNotFoundException;
import br.com.samp.financemanager.infrastructure.security.service.AuthenticatedUserService;
import br.com.samp.financemanager.model.Account;
import br.com.samp.financemanager.model.User;
import br.com.samp.financemanager.repository.AccountRepository;
import br.com.samp.financemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    public AccountResponse findById(UUID id) {
        User user = userAuthService.getAuthenticatedUser();

        Account account = accountRepository.findByHolderAndUuid(user,id)
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


    @Transactional
    public void delete(UUID id) {
        User user = userAuthService.getAuthenticatedUser();

        Account account = accountRepository.findByHolderAndUuid(user,id)
                .orElseThrow(()-> new ResourceNotFoundException("Account not found with id " + id));

        try {
            accountRepository.delete(account);
        }catch (DataIntegrityViolationException e) {
            throw new DataBaseException(
                    "The account cannot be deleted, as this would cause inconsistencies in the system.");
        }
    }

    public AccountResponse deposit(UUID id,Double amount) {
        if (amount < 0) throw new BusinessException("Amount cannot be negative");

        User user = userAuthService.getAuthenticatedUser();

        Account account = accountRepository.findByHolderAndUuid(user, id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        account.deposit(amount);

        return accountMapper.toAccountResponse(accountRepository.save(account));
    }

    public AccountResponse withdraw(UUID id,Double amount) {
        if (amount < 0) throw new BusinessException("Amount cannot be negative");

        User user = userAuthService.getAuthenticatedUser();

        Account account = accountRepository.findByHolderAndUuid(user, id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));


        if (account.getBalance() < amount) throw new BusinessException("Insufficient balance");


        account.withdraw(amount);

        return accountMapper.toAccountResponse(accountRepository.save(account));
    }
}
