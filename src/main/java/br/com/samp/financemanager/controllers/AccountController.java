package br.com.samp.financemanager.controllers;

import br.com.samp.financemanager.dto.request.AccountRequest;
import br.com.samp.financemanager.dto.request.TransactionRequest;
import br.com.samp.financemanager.dto.response.AccountResponse;
import br.com.samp.financemanager.services.AccountService;
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
            @PathVariable Long id
    ){
        return ResponseEntity.ok(accountService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> save(
            @RequestBody AccountRequest accountRequest
    ) {
        AccountResponse response = accountService.save(accountRequest);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<AccountResponse> deposit(
            @PathVariable Long id,
            @RequestBody TransactionRequest transactionRequest
    ){
        AccountResponse response = accountService.deposit(id,transactionRequest.amount());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(
            @PathVariable Long id,
            @RequestBody TransactionRequest transactionRequest
    ){
        AccountResponse response = accountService.withdraw(id,transactionRequest.amount());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        accountService.delete(id);

        return ResponseEntity.noContent().build();
    }


}
