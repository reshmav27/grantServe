package com.cts.grantserve.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user", "documents", "applications"}) // Prevents StackOverflowError
@EqualsAndHashCode(exclude = {"user", "documents", "applications"})
public class Researcher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long researcherID;

    private String name;
    private LocalDate dob;
    private String gender;
    private String institution;
    private String department;
    private String contactInfo;
    private String status;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "researcher", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ResearcherDocument> documents;

    @OneToMany(mappedBy = "researcher")
    @JsonManagedReference(value = "researcher-applications")
    private List<GrantApplication> applications;
}