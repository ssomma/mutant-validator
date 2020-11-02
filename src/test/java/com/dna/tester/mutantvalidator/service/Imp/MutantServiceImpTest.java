package com.dna.tester.mutantvalidator.service.Imp;

import com.dna.tester.mutantvalidator.exception.InvalidDNASampleException;
import com.dna.tester.mutantvalidator.model.DNA;
import com.dna.tester.mutantvalidator.model.DNACandidateDTO;
import com.dna.tester.mutantvalidator.repository.DNARepository;
import com.dna.tester.mutantvalidator.service.MutantService;
import com.dna.tester.mutantvalidator.service.RedisSyncService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SpringBootTest()
public class MutantServiceImpTest {

    @Mock
    private DNARepository dnaRepository;
    @Mock
    private RedisSyncService redisSyncService;

    @InjectMocks
    private MutantServiceImp mutantServiceImp;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testReturnsMutantWithSave(){
        String[] dna = new String[]{"ATGCGA", "CAGTGC","TTTTGT","AGAATG","CCACTA","TCACTG"};
        DNACandidateDTO dnaCandidateDTO = DNACandidateDTO.builder().withDna(dna).build();
        DNA dnaResult = DNA.builder()
                .withDnaReceived(Arrays.toString(dna))
                .withMutantResultCode(200)
                .withMutantResultText("Mutant")
                .build();

        ResponseEntity result = mutantServiceImp.ProcessMutantValidator(dnaCandidateDTO);
        Assert.assertEquals(200,result.getStatusCodeValue());
        verify(dnaRepository).save(dnaResult);
        verify(redisSyncService).incrStat(dnaResult.getMutantResultText());
    }

    @Test
    void testReturnsHumanWithSave(){
        String[] dna = new String[]{"ATGCGA","CAGTGC","TCTTGT","AGAATG","CCACTA","TCACTG"};
        DNACandidateDTO dnaCandidateDTO = DNACandidateDTO.builder().withDna(dna).build();
        DNA dnaResult = DNA.builder()
                .withDnaReceived(Arrays.toString(dna))
                .withMutantResultCode(403)
                .withMutantResultText("Human")
                .build();

        ResponseEntity result = mutantServiceImp.ProcessMutantValidator(dnaCandidateDTO);
        verify(dnaRepository).save(dnaResult);
        verify(redisSyncService).incrStat(dnaResult.getMutantResultText());
        Assert.assertEquals(403,result.getStatusCodeValue());
    }

    @Test
    void testReturnsDNASampleExceptionInvalidGeneLetter(){
        String[] dna = new String[]{"ZHLDZR","CAGTGC","TCTTGT","AGAATG","CCACTA","TCACTG"};
        DNACandidateDTO dnaCandidateDTO = DNACandidateDTO.builder().withDna(dna).build();

        Exception exception = Assert.assertThrows(InvalidDNASampleException.class,() -> { mutantServiceImp.ProcessMutantValidator(dnaCandidateDTO);});

        String expectedMessage = "Invalid gene. The gene sample Z is not contained on types ACTG";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testReturnsDNASampleExceptionInvalidGeneNumber(){
        String[] dna = new String[]{"CAGTGC","123234","TCTTGT","AGAATG","CCACTA","TCACTG"};
        DNACandidateDTO dnaCandidateDTO = DNACandidateDTO.builder().withDna(dna).build();

        Exception exception = Assert.assertThrows(InvalidDNASampleException.class,() -> { mutantServiceImp.ProcessMutantValidator(dnaCandidateDTO);});

        String expectedMessage = "Invalid gene. The gene sample 2 is not contained on types ACTG";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testReturnsDNASampleExceptionInvalidRowSizeGreaterRow(){
        String[] dna = new String[]{"CAGTGC","CAGTGC","TCTTGAAAT","AGAATG","CCACTA","TCACTG"};
        DNACandidateDTO dnaCandidateDTO = DNACandidateDTO.builder().withDna(dna).build();

        Exception exception = Assert.assertThrows(InvalidDNASampleException.class,() -> { mutantServiceImp.ProcessMutantValidator(dnaCandidateDTO);});

        String expectedMessage = "Invalid Row at 2. DNA Sample should be square.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testReturnsDNASampleExceptionInvalidRowSizeSmallerRow(){
        String[] dna = new String[]{"CAGTGC","G","TCTTGT","AGAATG","CCACTA","TCACTG"};
        DNACandidateDTO dnaCandidateDTO = DNACandidateDTO.builder().withDna(dna).build();

        Exception exception = Assert.assertThrows(InvalidDNASampleException.class,() -> { mutantServiceImp.ProcessMutantValidator(dnaCandidateDTO);});

        String expectedMessage = "Invalid Row at 1. DNA Sample should be square.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


}
