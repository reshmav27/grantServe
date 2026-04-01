package com.cts.grantserve.entity;

import com.cts.grantserve.enums.NotificationCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data               // Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor  // Generates the required empty constructor for JPA
@AllArgsConstructor // Generates a constructor with all fields
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationID;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    private Long entityID;
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationCategory category;

    private String status; // Read/Unread
    private LocalDate createdDate;
}