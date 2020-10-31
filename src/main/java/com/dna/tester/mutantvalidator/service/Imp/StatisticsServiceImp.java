package com.dna.tester.mutantvalidator.service.Imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dna.tester.mutantvalidator.service.RedisSyncService;
import com.dna.tester.mutantvalidator.service.StatisticsService;
import com.dna.tester.mutantvalidator.model.DNAStat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class StatisticsServiceImp implements StatisticsService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private RedisSyncService redisSyncService;

    @Autowired
    public StatisticsServiceImp(RedisSyncService redisSyncService) {
        log.info("Creating StatisticsService Instance.");
        this.redisSyncService = redisSyncService;
    }

    @Override
    public ResponseEntity retrieveMutantToHumanRatio() {
        log.info("Retrieving Mutant to Human ratio.");
        DNAStat mutantStat = redisSyncService.find("Mutant");
        DNAStat humanStat = redisSyncService.find("Human");
        Float mutantToHumanRatio = mutantStat.getResult() / humanStat.getResult().floatValue();

        String statsResponse = String.format("{\"count_mutant_dna\": \"%d\", \"count_human_dna\": \"%d\", \"ratio\" : \"%f\"}", humanStat.getResult(), mutantStat.getResult(), mutantToHumanRatio);
        log.debug("Request result: " + statsResponse);

        return new ResponseEntity<>(statsResponse,HttpStatus.OK);
    }
}