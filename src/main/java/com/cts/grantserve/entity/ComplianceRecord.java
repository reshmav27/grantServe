package com.cts.grantserve.entity;

import com.cts.grantserve.enums.ComplianceResult;
import com.cts.grantserve.enums.ComplianceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="Compliance_record")
public class ComplianceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complianceID;

    private Long entityID; // Can be ApplicationID or ProgramID

    @Enumerated(EnumType.STRING)
    private ComplianceType type;

    @Enumerated(EnumType.STRING)
    private ComplianceResult result;

    private java.time.LocalDate date;
    private String notes;
}