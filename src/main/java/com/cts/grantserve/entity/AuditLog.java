package com.cts.grantserve.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditID;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String action;
    private String resource;
    private LocalDateTime timestamp;
}