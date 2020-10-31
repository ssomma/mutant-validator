package com.dna.tester.mutantvalidator.service.Imp;

import com.dna.tester.mutantvalidator.model.DNAStat;
import com.dna.tester.mutantvalidator.service.RedisSyncService;
import com.dna.tester.mutantvalidator.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImp implements StatisticsService {

    private RedisSyncService redisSyncService;

    @Autowired
    public StatisticsServiceImp(RedisSyncService redisSyncService) {
        this.redisSyncService = redisSyncService;
    }

    @Override
    public ResponseEntity retrieveMutantToHumanRatio() {
        DNAStat mutantStat = redisSyncService.find("Mutant");
        DNAStat humanStat = redisSyncService.find("Human");
        Float mutantToHumanRatio = mutantStat.getResult() / humanStat.getResult().floatValue();

        String statsResponse = String.format("{\"count_mutant_dna\": \"%d\", \"count_human_dna\": \"%d\", \"ratio\" : \"%f\"}", humanStat.getResult(), mutantStat.getResult(), mutantToHumanRatio);


        return new ResponseEntity<>(statsResponse,HttpStatus.OK);
    }
}