package com.dna.tester.mutantvalidator.controller;

import com.dna.tester.mutantvalidator.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MutantStatisticsController {

    private final StatisticsService statisticsService;

    @Autowired
    public MutantStatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/stats")
    ResponseEntity getDNAStats() {
        return statisticsService.retrieveMutantToHumanRatio();
    }
}
