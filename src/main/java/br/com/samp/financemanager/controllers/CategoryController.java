package br.com.samp.financemanager.controllers;

import br.com.samp.financemanager.dto.request.CategoryRequest;
import br.com.samp.financemanager.dto.request.CategoryUpdateRequest;
import br.com.samp.financemanager.dto.response.CategoryResponse;
import br.com.samp.financemanager.dto.response.EarningResponse;
import br.com.samp.financemanager.dto.response.ExpenseResponse;
import br.com.samp.financemanager.services.CategoryService;
import br.com.samp.financemanager.services.EarningService;
import br.com.samp.financemanager.services.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private EarningService earningService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/{id}/expenses")
    public ResponseEntity<List<ExpenseResponse>> findExpensesByCategoryId
            (@PathVariable UUID id) {
        return ResponseEntity.ok(expenseService.findByCategoryUuid(id));
    }

    @GetMapping("/{id}/earnings")
    public ResponseEntity<List<EarningResponse>> findEarningsByCategoryId
            (@PathVariable UUID id) {
        return ResponseEntity.ok(earningService.findByCategoryUuid(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> save(
            @Valid @RequestBody CategoryRequest categoryRequest
    ) {
        CategoryResponse response = categoryService.createCategory(categoryRequest);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.uuid())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody CategoryUpdateRequest categoryRequest
            ){
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        categoryService.deleteCategoryByUuid(id);

        return ResponseEntity.noContent().build();
    }
}
