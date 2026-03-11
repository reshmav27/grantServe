package com.cts.grantserve.controller;


import com.cts.grantserve.entity.GrantApplication;
import com.cts.grantserve.service.GrantApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/GrantApplication")
public class GrantApplicationController {
    @Autowired
    GrantApplicationService grantApplicationService;
    @PostMapping("/createApplication")
    public ResponseEntity<String> createApplication(@RequestBody GrantApplication grantApplication){
        return ResponseEntity.status(HttpStatus.CREATED).body(grantApplicationService.createApplication(grantApplication));
    }
    @DeleteMapping("/DeleteApplication/{id}")
    public ResponseEntity<String> DelteApplication(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(grantApplicationService.DeleteApplication(id));

    }
    @GetMapping("getApplication/{id}")
    public ResponseEntity<GrantApplication> getApplication(@PathVariable int id) {
        return grantApplicationService.getApplication(id)
                .map(app -> ResponseEntity.ok(app))
                .orElse(ResponseEntity.notFound().build());
    }
}
