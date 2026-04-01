package com.cts.grantserve.entity;

import com.cts.grantserve.enums.ReportScope;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data               // Generates all Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor  // Required by JPA/Hibernate
@AllArgsConstructor // Useful for creating reports in your Service layer
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportID;

    @Enumerated(EnumType.STRING)
    private ReportScope scope;

    @Column(columnDefinition = "TEXT") // Good practice for JSON/Metrics strings
    private String metrics;

    private LocalDate generatedDate;
}