package com.cts.grantserve.controller;

import com.cts.grantserve.entity.Proposal;
import com.cts.grantserve.exception.ProposalException;
import com.cts.grantserve.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/proposal")
public class ProposalController {

    @Autowired
    ProposalService proposalService;

    @PostMapping("/createProposal")
    public ResponseEntity<String> createProposal(@RequestBody Proposal proposal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proposalService.createProposal(proposal));
    }
}
