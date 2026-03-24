package com.cts.grantserve.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Disbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long disbursementID;

    private Double amount;
    private java.time.LocalDate date;
    private String status;

    @ManyToOne
    @JoinColumn(name = "applicationID")
    @JsonBackReference
    private GrantApplication application;

    @OneToOne(mappedBy = "disbursement", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Payment payment;


}