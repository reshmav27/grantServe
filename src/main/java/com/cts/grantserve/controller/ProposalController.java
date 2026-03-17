package com.cts.grantserve.controller;

import com.cts.grantserve.DTO.ProposalDto;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.service.IProposalService;
import com.cts.grantserve.service.ProposalServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proposal")
public class ProposalController {

    @Autowired
    IProposalService proposalService;

    @PostMapping("/createProposal")
    public ResponseEntity<String> createProposal(@Valid @RequestBody ProposalDto proposal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proposalService.createProposal(proposal));
    }
}
