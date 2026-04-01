package com.cts.grantserve.entity;
import com.cts.grantserve.enums.Result;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evaluationID;

    @OneToOne
    @JoinColumn(name = "applicationID",nullable = false)
    @JsonIgnore
    private GrantApplication application;

    @Enumerated(EnumType.STRING)
    private Result result;

    private java.time.LocalDate date;
    private String notes;
}