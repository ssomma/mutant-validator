package com.dna.tester.mutantvalidator.controller;

import com.dna.tester.mutantvalidator.exception.DBSyncException;
import com.dna.tester.mutantvalidator.model.DNACandidateDTO;
import com.dna.tester.mutantvalidator.service.MutantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class MutantController {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final MutantService mutantService;

    @Autowired
    public MutantController(MutantService mutantService){
        log.info("Initializing MutantController class.");
        this.mutantService = mutantService;
    }

    @PostMapping("/mutant")
    ResponseEntity checkMutant(@RequestBody DNACandidateDTO DNACandidate) throws DBSyncException {
        log.info("Initializing MutantController method.");
        if(DNACandidate.getDna() == null) throw new RuntimeException();
        return mutantService.ProcessMutantValidator(DNACandidate);
    }

}
