package com.sbnz.SIEMCenter2.controller;

import com.sbnz.SIEMCenter2.dto.RuleDTO;
import com.sbnz.SIEMCenter2.service.KieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/rule")
@CrossOrigin("*")
public class RuleController {
    @Autowired
    private KieService kieService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> setRule(@RequestBody RuleDTO rule ){
        try {
            kieService.insertNewRule(rule.rule, rule.interval, rule.count, rule.message);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
