package com.cts.grantserve.DTO;
import com.cts.grantserve.enums.DocType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResearcherDocumentDto {

    @NotNull(message = "Researcher ID is required")
    private Long researcherID;

    @NotNull(message = "Document type is required")
    private DocType docType;

    @NotBlank(message = "File URI is required")
    private String fileURI;
}
