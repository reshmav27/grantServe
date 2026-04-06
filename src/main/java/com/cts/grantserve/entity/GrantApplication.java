package com.cts.grantserve.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class GrantApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationID;

    @ManyToOne
    @JoinColumn(name = "researcherID")
    @JsonBackReference(value = "researcher-applications")
    private Researcher researcher;

    @ManyToOne
    @JoinColumn(name = "programID")
    @JsonBackReference(value = "program-applications")
    private Program program;

    @OneToMany(mappedBy = "grantApplication", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Proposal> proposals= new ArrayList<>();

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true )
    @JsonManagedReference
    private List<Disbursement> disbursement;

    @OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "application-allocation")
    private Allocation allocation;

    private String title;
    private java.time.LocalDate submittedDate;
    private String status;
}