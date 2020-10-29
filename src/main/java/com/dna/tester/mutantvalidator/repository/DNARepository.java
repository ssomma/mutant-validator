package com.dna.tester.mutantvalidator.repository;

import com.dna.tester.mutantvalidator.model.DNA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DNARepository extends JpaRepository<DNA, Long> {

    List<DNA> findDNAById(Long id);

    @Query(value = "SELECT * FROM DNADB.DNACatalog WHERE request_date BETWEEN ?1 AND ?2 ", nativeQuery = true)
    List<DNA> findByRequestsByDateCloseInterval( String dateSince, String dateTo);

}
