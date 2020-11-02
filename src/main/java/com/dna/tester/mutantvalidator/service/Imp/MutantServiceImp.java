package com.dna.tester.mutantvalidator.service.Imp;
import java.util.Arrays;

import com.dna.tester.mutantvalidator.exception.InvalidDNASampleException;
import org.jetbrains.annotations.NotNull;
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


@SuppressWarnings({"ALL", "unchecked"})
@Service
public class MutantServiceImp implements MutantService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private DNARepository dnaRepository;
    private RedisSyncService redisSyncService;

    private final String GENE_TYPES = "ACTG";

    private final int PATTERN_LENGTH = 4;

    @Autowired
    public MutantServiceImp(DNARepository dnaRepository, RedisSyncService redisSyncService) {
        log.info("Initializing MutantService.");
        this.dnaRepository = dnaRepository;
        this.redisSyncService = redisSyncService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResponseEntity<?> ProcessMutantValidator(@NotNull DNACandidateDTO dnaCandidateDTO){
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
                    mutantCandidate = validateAdjacentGene(dna, gene, rowSize, colSize, rowPosition, colPosition);
                    if (mutantCandidate) {
                        return true;
                    }
                }
                colPosition++;
            }
        }
        return false;
    }

    private boolean validateAdjacentGene(String[] dna, Character gene, int rowSize, int columnSize, int initialRow, int initialCol){

        int[] rowDirections = {0,0,1,1,1,-1,-1,-1};
        int[] colDirections = {1,-1,1,0,-1,1,0,-1};
        char geneValue = dna[initialRow].charAt(initialCol);
        validGeneChecker(geneValue);
        if(geneValue != gene) return false;

        for(int dirCandidate = 0; dirCandidate < 8; dirCandidate++){

            int cellCandidateRow = initialRow + rowDirections[dirCandidate];
            int cellCandidateCol = initialCol + colDirections[dirCandidate];
            int patternIndex;
            for(patternIndex = 1 ; patternIndex < PATTERN_LENGTH; patternIndex++){

                if(cellCandidateRow >= rowSize || cellCandidateCol >= columnSize || cellCandidateRow < 0 || cellCandidateCol < 0) break;
                geneValue = dna[cellCandidateRow].charAt(cellCandidateCol);
                validGeneChecker(geneValue);
                if(geneValue != gene) break;

                cellCandidateRow += rowDirections[dirCandidate];
                cellCandidateCol += colDirections[dirCandidate];

            }
            if(patternIndex == PATTERN_LENGTH) return true;
        }
        return false;
    }


    // Validadores de datos
    private void validDNAChecker(String[] dna){
        int rowSize = dna.length;
        int colSize;

        for(int row = 0; row < rowSize; row++){
            colSize = dna[row].length();
            if(rowSize != colSize)
                throw new InvalidDNASampleException(String.format("Invalid Row at %d. DNA Sample should be square.", row));
        }

    }

    private void validGeneChecker(Character gene){
        if(!GENE_TYPES.contains(gene.toString())) {
            throw  new InvalidDNASampleException(String.format("Invalid gene. The gene sample %s is not contained on types %s", gene, GENE_TYPES));
        }
    }


    // Métodos de persistencia
    private void saveDNAEntity(String[] dna, int resultCode, String resultText){

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
