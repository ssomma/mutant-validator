package com.dna.tester.mutantvalidator.service.Imp;

import com.dna.tester.mutantvalidator.repository.DNAStatsRepository;
import com.dna.tester.mutantvalidator.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class StatisticsServiceImp implements StatisticsService {

    private DNAStatsRepository dnaStatsRepository;

    @Autowired
    public StatisticsServiceImp(DNAStatsRepository dnaStatsRepository) {
        this.dnaStatsRepository = dnaStatsRepository;
    }


    @Override
    public ResponseEntity retrieveDNAStats() {
        dnaStatsRepository.findAll();
        return new ResponseEntity(HttpStatus.OK);
    }
}