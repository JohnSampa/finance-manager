package br.com.samp.financemanager.controllers;

import br.com.samp.financemanager.dto.request.AccountRequest;
import br.com.samp.financemanager.dto.request.TransactionRequest;
import br.com.samp.financemanager.dto.response.AccountResponse;
import br.com.samp.financemanager.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class    AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAccounts(){
        return ResponseEntity.ok(accountService.listAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById(
            @PathVariable UUID id
    ){
        return ResponseEntity.ok(accountService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> save(
           @Valid @RequestBody AccountRequest accountRequest
    ) {
        AccountResponse response = accountService.save(accountRequest);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.uuid())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountResponse> deposit(
            @PathVariable UUID id,
            @Valid @RequestBody TransactionRequest transactionRequest
    ){
        AccountResponse response = accountService.deposit(id,transactionRequest.amount());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(
            @PathVariable UUID id,
            @Valid @RequestBody TransactionRequest transactionRequest
    ){
        AccountResponse response = accountService.withdraw(id,transactionRequest.amount());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){

        accountService.delete(id);

        return ResponseEntity.noContent().build();
    }


}
