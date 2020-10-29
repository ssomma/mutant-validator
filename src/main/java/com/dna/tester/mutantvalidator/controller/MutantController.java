package com.dna.tester.mutantvalidator.controller;

import com.dna.tester.mutantvalidator.model.DNA;
import com.dna.tester.mutantvalidator.model.DNACandidateDTO;

import com.dna.tester.mutantvalidator.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class MutantController {
    private final MutantService mutantService;

    @Autowired
    public MutantController(MutantService mutantService) {
        this.mutantService = mutantService;
    }

    @PostMapping("/mutant")
    ResponseEntity checkMutant(@RequestBody DNACandidateDTO DNACandidate) {
        if(DNACandidate.getDna() == null) throw new RuntimeException();
        return mutantService.ProcessMutantValidator(DNACandidate);
    }

}
