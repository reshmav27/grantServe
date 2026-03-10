package com.cts.grantserve.entity;


import jakarta.persistence.*;

    import java.time.LocalDate;

@Entity
@Table(name="ResearcherDocument")
    public class ResearcherDocument {

        @Id
        private String documentID;
        private String researcherID;
        private String docType;
        private String fileURI;
        private LocalDate uploadedDate;
        private String verificationStatus;

        // Getter and Setter Methods
        public String getDocumentID()
        {
            return documentID;
        }
        public void setDocumentID(String documentID)
        {
            this.documentID = documentID;
        }

        public String getResearcherID()
        {
            return researcherID;
        }
        public void setResearcherID(String researcherID)
        {
            this.researcherID = researcherID;
        }

        public String getDocType()
        {
            return docType;
        }
        public void setDocType(String docType)
        {
            this.docType = docType;
        }

        public String getFileURI()
        {
            return fileURI;
        }
        public void setFileURI(String fileURI)
        {
            this.fileURI = fileURI;
        }

        public LocalDate getUploadedDate()
        {
            return uploadedDate;
        }
        public void setUploadedDate(LocalDate uploadedDate)
        {
            this.uploadedDate = uploadedDate;
        }

        public String getVerificationStatus()
        {
            return verificationStatus;
        }
        public void setVerificationStatus(String verificationStatus)
        {
            this.verificationStatus = verificationStatus;
        }
}
