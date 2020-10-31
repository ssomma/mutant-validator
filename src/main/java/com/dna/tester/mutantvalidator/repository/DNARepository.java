package com.dna.tester.mutantvalidator.repository;

import com.dna.tester.mutantvalidator.model.DNA;
import com.dna.tester.mutantvalidator.model.DNAStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DNARepository extends JpaRepository<DNA, Long> {


    List<DNA> findDNAById(Long id);

    @Query(value = "SELECT DISTINCT mutant_result_text FROM dnadb.dnacatalog", nativeQuery = true)
    List<String> distinctMutantResult();

    Long countByMutantResultText(String mutantResultText);

}
