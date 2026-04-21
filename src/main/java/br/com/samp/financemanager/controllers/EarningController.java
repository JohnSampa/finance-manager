package br.com.samp.financemanager.controllers;

import br.com.samp.financemanager.dto.request.EarningRequest;
import br.com.samp.financemanager.dto.response.EarningResponse;
import br.com.samp.financemanager.model.enums.TransactionStatus;
import br.com.samp.financemanager.services.EarningService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping("/earnings")
public class EarningController {

    @Autowired
    private EarningService earningService;

    @GetMapping
    public ResponseEntity<List<EarningResponse>> findAll(
            @RequestParam(required = false)
            UUID categoryId,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DATE)
            LocalDate date,

            @RequestParam(required = false)
            TransactionStatus status
    ) {
        return ResponseEntity.ok(earningService.find(categoryId, date, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EarningResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(earningService.findByUUID(id));
    }

    @PostMapping
    public ResponseEntity<EarningResponse> save(
            @Valid @RequestBody EarningRequest earningRequest
    ) {
        EarningResponse response = earningService.save(earningRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.uuid())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        earningService.deleteByUUID(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<EarningResponse> confirm(
            @PathVariable UUID id
    ) {
        EarningResponse response = earningService.confirmEarning(id);
        return ResponseEntity.ok().body(response);
    }

}
