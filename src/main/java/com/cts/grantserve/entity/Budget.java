package com.cts.grantserve.entity;

import com.cts.grantserve.enums.BudgetStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Long budgetID;

    private Double allocatedAmount;
    private Double spentAmount;
    private Double remainingAmount;

    @Enumerated(EnumType.STRING)
    private BudgetStatus status;

    @OneToOne
    @JoinColumn(name = "program_id")
    @JsonBackReference
    private Program program;


    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "budget-allocation")
    private List<Allocation> allocations;

}
