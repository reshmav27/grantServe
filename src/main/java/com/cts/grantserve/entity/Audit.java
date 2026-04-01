package com.cts.grantserve.entity;

import com.cts.grantserve.enums.AuditScope;
import com.cts.grantserve.enums.AuditStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditID;
    private Long officerID;

    @Enumerated(EnumType.STRING)
    private AuditScope scope;
    private String findings;
    private java.time.LocalDate date;
    @Enumerated(EnumType.STRING)
    private AuditStatus status;
}