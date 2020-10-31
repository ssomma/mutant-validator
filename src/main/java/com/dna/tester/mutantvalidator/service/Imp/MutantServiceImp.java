package com.dna.tester.mutantvalidator.service.Imp;
import java.util.Arrays;

import com.dna.tester.mutantvalidator.configuration.AppConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.dna.tester.mutantvalidator.exception.DBSyncException;
import com.dna.tester.mutantvalidator.model.DNA;
import com.dna.tester.mutantvalidator.model.DNACandidateDTO;
import com.dna.tester.mutantvalidator.repository.DNARepository;
import com.dna.tester.mutantvalidator.service.MutantService;
import com.dna.tester.mutantvalidator.service.RedisSyncService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import static com.dna.tester.mutantvalidator.utils.DNAMutantValidator.*;


@Service
public class MutantServiceImp implements MutantService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private DNARepository dnaRepository;
    private RedisSyncService redisSyncService;
    private String GENE_TYPES;
    private int PATTERN_LENGTH;

    @Autowired
    public MutantServiceImp(AppConfiguration appConfiguration, DNARepository dnaRepository, RedisSyncService redisSyncService) {
        log.info("Initializing MutantService.");
        this.dnaRepository = dnaRepository;
        this.GENE_TYPES = appConfiguration.getGeneTypes();
        this.PATTERN_LENGTH = appConfiguration.getMutantPatternLength();
        this.redisSyncService = redisSyncService;
    }

    @Override
    public ResponseEntity ProcessMutantValidator(DNACandidateDTO dnaCandidateDTO) throws DBSyncException {
        log.info("Start validation of mutant DNA");
        String[] dnaCandidate = dnaCandidateDTO.getDna();
        ResponseEntity<String> mutantResult;
        boolean isMutant = isMutant(dnaCandidate);
        log.debug(String.format("Mutant validation result: %b, save on database.", isMutant));

        if(isMutant) mutantResult = new ResponseEntity("Mutant", HttpStatus.OK);
        else mutantResult = new ResponseEntity("Human", HttpStatus.FORBIDDEN);

        saveDNAEntity(dnaCandidate,mutantResult.getStatusCodeValue(),mutantResult.getBody());
        log.debug("DNA Validation result saved on the database.");

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
    private void saveDNAEntity(String[] dna, int resultCode, String resultText) throws DBSyncException {

            DNA dnaEntity = DNA.builder()
                    .withDnaReceived(Arrays.toString(dna))
                    .withMutantResultCode(resultCode)
                    .withMutantResultText(resultText)
                    .build();

            try{
                dnaRepository.save(dnaEntity);
                redisSyncService.incrStat(resultText);
            }

            catch (Exception ex){
                throw new DBSyncException("Error at syncing with Database.");
            }
    }


}
