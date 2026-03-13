package br.com.samp.financemanager.controllers;

import br.com.samp.financemanager.dto.request.AccountRequest;
import br.com.samp.financemanager.dto.request.TransactionRequest;
import br.com.samp.financemanager.dto.response.AccountResponse;
import br.com.samp.financemanager.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/accounts")
public class    AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findUserAccounts(@PathVariable Long userId){
        return ResponseEntity.ok(accountService.listUserAccounts(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById(
            @PathVariable Long userId,
            @PathVariable Long id
    ){
        return ResponseEntity.ok(accountService.findByUserAndAccountId(userId,id));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> save(
            @PathVariable Long userId,
            @RequestBody AccountRequest accountRequest
    ) {
        AccountResponse response = accountService.save(userId, accountRequest);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountResponse> deposit(
            @PathVariable Long userId,
            @PathVariable Long id,
            @RequestBody TransactionRequest transactionRequest
    ){
        AccountResponse response = accountService.deposit(userId,id,transactionRequest.amount());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long id){

        accountService.delete(userId,id);

        return ResponseEntity.noContent().build();
    }


}
