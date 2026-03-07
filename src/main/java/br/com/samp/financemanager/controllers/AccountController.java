package br.com.samp.financemanager.controllers;

import br.com.samp.financemanager.dto.AccountRequest;
import br.com.samp.financemanager.dto.AccountResponse;
import br.com.samp.financemanager.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/users/{id}/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll(@PathVariable Long id){
        return ResponseEntity.ok(accountService.listUserAccounts(id));
    }

    @GetMapping("{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(accountService.findAccountById(id));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> save(
            @PathVariable Long id,
            @RequestBody AccountRequest accountRequest
    ) {
        AccountResponse response = accountService.save(id, accountRequest);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }



}
