package com.dna.tester.mutantvalidator.service;

import com.dna.tester.mutantvalidator.model.DNACandidateDTO;
import org.springframework.http.ResponseEntity;

public interface MutantService {
    ResponseEntity ProcessMutantValidator(DNACandidateDTO dnaCandidateDTO);
}
