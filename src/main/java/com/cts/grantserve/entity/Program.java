package com.cts.grantserve.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

import com.cts.grantserve.enums.ProgramStatus;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long programID;

    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double budget;
    private int count;

    @Enumerated(EnumType.STRING)
    private ProgramStatus status;

    @OneToOne(mappedBy = "program", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Budget budgetRecord;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "program-applications")
    private List<GrantApplication> applications;

}