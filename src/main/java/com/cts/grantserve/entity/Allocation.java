package com.cts.grantserve.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "allocation")
public class Allocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long allocationID;

    private Long programID;

    @OneToOne
    @JsonBackReference(value = "application-allocation")
    @JoinColumn(name = "applicationID", nullable = false)
    private GrantApplication application;

    @ManyToOne
    @JsonBackReference(value = "budget-allocation")
    @JoinColumn(name = "budgetID", nullable = false)
    private Budget budget;

    private Double totalAwardedAmount;

    private Double disbursedAmount = 0.0;

    private Double remainingBalance;

    private String status;


}