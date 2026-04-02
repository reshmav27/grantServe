package com.cts.grantserve.controller;

import com.cts.grantserve.dto.ProposalDto;
import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.projection.IProposalProjection;
import com.cts.grantserve.service.IProposalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proposal")
public class ProposalController {

    @Autowired
    IProposalService proposalService;

    @PostMapping("/createProposal")
    public ResponseEntity<String> createProposal(@Valid @RequestBody ProposalDto proposal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proposalService.createProposal(proposal));
    }

    @GetMapping("/getProposal/{proposalId}")
    public ResponseEntity<List<IProposalProjection>> getProposal(@PathVariable Long proposalId){
        return ResponseEntity.status(200).body(proposalService.getProposal(proposalId));

    }

    @GetMapping("/getRecentProposals/{applicationId}")
    public ResponseEntity<List<IProposalProjection>> getRecentProposals(@PathVariable Long applicationId){
        return ResponseEntity.ok(proposalService.getRecentProposals(applicationId));
    }



}
