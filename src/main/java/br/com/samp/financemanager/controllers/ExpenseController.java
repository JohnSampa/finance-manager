package br.com.samp.financemanager.controllers;

import br.com.samp.financemanager.dto.request.ExpenseRequest;
import br.com.samp.financemanager.dto.response.ExpenseResponse;
import br.com.samp.financemanager.services.ExpenseService;
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

@RestController
@RequestMapping("/users/{userId}/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> findAllByUserId(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(expenseService.findByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> findByUserIdAndExpenseId(
            @PathVariable Long userId,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(expenseService.findByUserIdAndId(userId,id));
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> save(
            @PathVariable Long userId,
            @Valid @RequestBody ExpenseRequest expenseRequest
    ) {
        ExpenseResponse response = expenseService.saveExpense(userId, expenseRequest);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<ExpenseResponse> confirmExpense(
            @PathVariable Long userId,
            @PathVariable Long expenseId
    ){
        ExpenseResponse response = expenseService.confirmExpense(userId, expenseId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(
            @PathVariable Long userId,
            @PathVariable Long expenseId
    ) {
        expenseService.deleteExpense(userId, expenseId);

        return ResponseEntity.noContent().build();
    }
}
