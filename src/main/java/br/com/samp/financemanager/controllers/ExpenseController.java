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
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> findAllByUserId() {
        return ResponseEntity.ok(expenseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(expenseService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> save(
            @RequestBody ExpenseRequest expenseRequest
    ) {
        ExpenseResponse response = expenseService.save(expenseRequest);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<ExpenseResponse> confirmExpense(
            @PathVariable Long id
    ) {
        ExpenseResponse response = expenseService.confirmExpense(id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(
            @PathVariable Long id
    ) {
        expenseService.deleteExpense(id);

        return ResponseEntity.noContent().build();
    }
}
