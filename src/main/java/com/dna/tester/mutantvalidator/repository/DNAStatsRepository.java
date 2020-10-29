package com.dna.tester.mutantvalidator.repository;

import com.dna.tester.mutantvalidator.model.DNAStat;


import java.util.Map;

public interface DNAStatsRepository {

    void save(DNAStat dnaStat);
    Map<String, DNAStat> findAll();
    DNAStat findById(String id);
    void update(DNAStat dnaStat);
    void delete(String id);

}
