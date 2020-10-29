package com.dna.tester.mutantvalidator.service.Imp;
import java.util.Arrays;
import java.util.logging.Logger;

import com.dna.tester.mutantvalidator.model.DNA;
import com.dna.tester.mutantvalidator.model.DNACandidateDTO;
import com.dna.tester.mutantvalidator.model.DNAStat;
import com.dna.tester.mutantvalidator.repository.DNARepository;
import com.dna.tester.mutantvalidator.repository.DNAStatsRepository;
import com.dna.tester.mutantvalidator.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.dna.tester.mutantvalidator.utils.DNAMutantValidator.*;


@Service
public class MutantServiceImp implements MutantService {


    private final Logger log = Logger.getLogger(this.getClass().getName());
    private DNARepository dnaRepository;
    private DNAStatsRepository dnaStatsRepository;


    private static final String GENE_TYPES = "ACTG";
    private static final int PATTERN_LENGTH = 4;

    @Autowired
    public MutantServiceImp(DNARepository dnaRepository, DNAStatsRepository dnaStatsRepository) {
        this.dnaRepository = dnaRepository;
        this.dnaStatsRepository = dnaStatsRepository;
    }

    // process mutant es el que va al controlador, llama a isMutant

    @Override
    public ResponseEntity ProcessMutantValidator(DNACandidateDTO dnaCandidateDTO){

        String[] dnaCandidate = dnaCandidateDTO.getDna();
        ResponseEntity<String> mutantResult;
        boolean isMutant = isMutant(dnaCandidate);

        if(isMutant) mutantResult = new ResponseEntity("Mutant", HttpStatus.OK);
        else mutantResult = new ResponseEntity("Human", HttpStatus.FORBIDDEN);
        saveDNAEntity(dnaCandidate,mutantResult.getStatusCodeValue(),mutantResult.getBody());

        return mutantResult;


    }


    private boolean isMutant(String[] dna) {

        validDNAChecker(dna);
        int rowSize = dna.length;
        int colSize = dna[0].length();
        boolean mutantCandidate;

        for(int rowPosition = 0; rowPosition < rowSize; rowPosition++){
            int colPosition = 0;
            while (colPosition < colSize) {
                for (Character gene : GENE_TYPES.toCharArray()) {
                    mutantCandidate = validateAdjacentGene(dna, gene, PATTERN_LENGTH, GENE_TYPES, rowSize, colSize, rowPosition, colPosition);
                    if (mutantCandidate) {
                        return true;
                    }
                }
                colPosition++;
            }
        }
        return false;
    }

    // Métodos de persistencia

    private void saveDNAEntity(String[] dna, int resultCode, String resultText){

            DNA dnaEntity = DNA.builder()
                    .withDnaReceived(Arrays.toString(dna))
                    .withMutantResultCode(resultCode)
                    .withMutantResultText(resultText)
                    .build();

            Long dnaStatValue = dnaStatsRepository.findById(resultText).getValue();

            DNAStat dnaStatEntity =  DNAStat.builder()
                    .withKey(resultText)
                    .withValue(dnaStatValue + 1)
                    .build();

            try{
                dnaRepository.save(dnaEntity);
                dnaStatsRepository.save(dnaStatEntity);
            }
            catch(Exception ex){
                 throw new RuntimeException(ex);
            }
    }


}
