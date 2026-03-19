package com.cts.grantserve.controller;

import com.cts.grantserve.dto.BudgetDto;
import com.cts.grantserve.entity.Budget;
import com.cts.grantserve.exception.BudgetNotFoundException;
import com.cts.grantserve.exception.ProgramNotFoundException;
import com.cts.grantserve.exception.ProgramNotModifiableException;
import com.cts.grantserve.service.IBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/budgets")
public class BudgetController {

    @Autowired
    private IBudgetService budgetService;

    // Get a budget by budget ID
    @GetMapping("{id}")
    public ResponseEntity<Budget> getBudgetById(@PathVariable Long id) {
        return budgetService.getBudget(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all budget records
    @GetMapping
    public ResponseEntity<List<Budget>> getAllBudgets() {
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
        String response = budgetService.allocateAmountToResearcherByBudgetId(budgetId, allocatedAmount);
        return ResponseEntity.ok(response);
    }

}