package com.dna.tester.mutantvalidator.controller;

import com.dna.tester.mutantvalidator.exception.InvalidDNASampleException;
import com.dna.tester.mutantvalidator.model.DNACandidateDTO;
import com.dna.tester.mutantvalidator.service.MutantService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

public class MutantControllerTest {

    @InjectMocks
    private MutantController mutantController;

    @Mock
    private MutantService mutantService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInvalidDNACandidateDTO() {
        DNACandidateDTO invalidCandidate = null;
        Assert.assertThrows(RuntimeException.class,() -> { mutantController.checkMutant(invalidCandidate);});
    }


    @Test
    public void testExpectedReturnFromMutant(){
        String[] dna = {"ATCGGT","ACTGAC","AAGTAC","AGAATG","CCAATA","TCACTG"};
        DNACandidateDTO validCandidateMutant = DNACandidateDTO.builder().withDna(dna).build();
        ResponseEntity responseExpected = new ResponseEntity("Mutant",HttpStatus.OK);

        doReturn(responseExpected).when(mutantService).ProcessMutantValidator(validCandidateMutant);
        ResponseEntity actualResponse = mutantController.checkMutant(validCandidateMutant);
        Assert.assertEquals(responseExpected,actualResponse);
    }

    @Test
    public void testExpectedReturnFromHuman(){
        String[] dna = {"ATCGGT","ACTGAC","AAGTAC","AGTGTG","CCGTTA","TCACTG"};
        DNACandidateDTO validCandidateMutant = DNACandidateDTO.builder().withDna(dna).build();
        ResponseEntity responseExpected = new ResponseEntity("Human",HttpStatus.FORBIDDEN);

        doReturn(responseExpected).when(mutantService).ProcessMutantValidator(validCandidateMutant);
        ResponseEntity actualResponse = mutantController.checkMutant(validCandidateMutant);
        Assert.assertEquals(responseExpected,actualResponse);
    }

}