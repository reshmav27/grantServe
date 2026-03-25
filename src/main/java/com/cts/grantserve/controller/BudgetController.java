package com.cts.grantserve.controller;

import com.cts.grantserve.entity.Budget;
import com.cts.grantserve.projection.IBudgetProjection;
import com.cts.grantserve.service.IBudgetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/budgets")
@Slf4j
public class BudgetController {

    @Autowired
    private IBudgetService budgetService;

    // Get a budget by budget ID
    @GetMapping("{id}")
    public ResponseEntity<IBudgetProjection> getBudgetById(@PathVariable Long id) {
        return budgetService.getBudget(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all budget records
    @GetMapping
    public ResponseEntity<List<IBudgetProjection>> getAllBudgets() {
        return ResponseEntity.ok(budgetService.getAllBudgets());
    }

    // Get budget by program ID
    @GetMapping("/program/{programId}")
    public ResponseEntity<Budget> getBudgetByProgram(@PathVariable Long programId) {
        return budgetService.getBudgetByProgram(programId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update a budget's allocation
    @PatchMapping("/{budgetId}")
    public ResponseEntity<String> allocateFundToResearcher(@PathVariable Long budgetId, @RequestParam Double allocatedAmount) {
        log.info("Received request to allocate funds. Budget ID: {}, Amount: {}", budgetId, allocatedAmount);
        String response = budgetService.allocateAmountToResearcherByBudgetId(budgetId, allocatedAmount);
        log.info("Successfully processed allocation for Budget ID: {}", budgetId);
        return ResponseEntity.ok(response);
    }

}