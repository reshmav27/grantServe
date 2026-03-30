package com.cts.grantserve.controller;


import com.cts.grantserve.dto.GrantApplicationDto;
import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.service.IGrantApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/GrantApplication")
public class GrantApplicationController {

    @Autowired
    private IGrantApplicationService iGrantApplicationService;

    @PostMapping("/createApplication")
    public ResponseEntity<String> createApplication(@Valid @RequestBody GrantApplicationDto grantApplication){
        return ResponseEntity.status(HttpStatus.CREATED).body(iGrantApplicationService.createApplication(grantApplication));
    }
    @GetMapping("/FetchGrantApplication/{id}")
    public ResponseEntity<List<GrantApplication>> FetchGrantApplication(@PathVariable Long id){
        return ResponseEntity.ok(iGrantApplicationService.FetchGrantApplication(id));
    }
    @DeleteMapping("/DeleteApplication/{id}")
    public ResponseEntity<String> DeleteApplication(@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(iGrantApplicationService.DeleteApplication(id));
    }
    @GetMapping("getApplication/{id}")
    public ResponseEntity<GrantApplication> getApplication(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(iGrantApplicationService.getApplication(id));
    }

    @GetMapping("/UserApplicationCount/{id}")
    public ResponseEntity<Map<String, Object>> getApplicationCount(@PathVariable Long id) {
        long count = iGrantApplicationService.getuserApplicationCount(id);
        Map<String, Object> response = new HashMap<>();
        String key = "Total number of applications for user " + id;
        response.put(key, count);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public List<GrantApplication> search(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String title) {
        return iGrantApplicationService.search(id, title);
    }



    @GetMapping("/ProgramGrantApplications/{programID}")
    public ResponseEntity<List<GrantApplication>> fetchProgramGrantApplications(@PathVariable Long programID){
        return
                iGrantApplicationService.fetchProgramGrantApplications(programID)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());

    }

}
