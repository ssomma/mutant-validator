package com.dna.tester.mutantvalidator.service;

import org.springframework.http.ResponseEntity;

public interface StatisticsService {
    ResponseEntity retrieveMutantToHumanRatio();
}
