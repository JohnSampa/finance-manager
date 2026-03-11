package br.com.samp.financemanager.controllers;

import br.com.samp.financemanager.dto.response.ExpenseResponse;
import br.com.samp.financemanager.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
