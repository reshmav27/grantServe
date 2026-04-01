package com.cts.grantserve.entity;

import com.cts.grantserve.enums.ReviewStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "proposal") // Prevents infinite loops when logging a Review
@EqualsAndHashCode(exclude = "proposal")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewID;

    @ManyToOne
    @JoinColumn(name = "proposalID")
    @JsonBackReference
    private Proposal proposal;

    @ManyToOne
    @JoinColumn(name = "reviewerid", nullable = false)
    private User reviewer;

    private Integer score;
    private String comments;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;
}