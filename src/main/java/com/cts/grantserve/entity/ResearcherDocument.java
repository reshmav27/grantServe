package com.cts.grantserve.entity;

import com.cts.grantserve.enums.DocType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "researcher") // Prevents infinite loops during logging
@EqualsAndHashCode(exclude = "researcher") // Prevents issues in Collections
public class ResearcherDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentID;

    @ManyToOne
    @JoinColumn(name = "researcher_id")
    @JsonBackReference
    private Researcher researcher;

    @Enumerated(EnumType.STRING)
    private DocType docType;

    private String fileURI;
    private LocalDateTime uploadedDate;
    private String verificationStatus;
}