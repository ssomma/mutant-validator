package com.dna.tester.mutantvalidator.repository;

import com.dna.tester.mutantvalidator.model.DNAStat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DNAStatsRepository extends CrudRepository <DNAStat, String> {
}
