package com.cts.grantserve.entity;


import com.cts.grantserve.enums.DocType;
import jakarta.persistence.*;

    import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class ResearcherDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentID;

    @ManyToOne
    @JoinColumn(name = "researcherID")
    private Researcher researcher;

    @Enumerated(EnumType.STRING)
    private DocType docType;

    public Long getDocumentID() {
        return documentID;
    }

    public void setDocumentID(Long documentID) {
        this.documentID = documentID;
    }

    public Researcher getResearcher() {
        return researcher;
    }

    public void setResearcher(Researcher researcher) {
        this.researcher = researcher;
    }

    public DocType getDocType() {
        return docType;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    public String getFileURI() {
        return fileURI;
    }

    public void setFileURI(String fileURI) {
        this.fileURI = fileURI;
    }

    public LocalDateTime getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(LocalDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    private String fileURI;
    private java.time.LocalDateTime uploadedDate;
    private String verificationStatus;
}
