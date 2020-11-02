package com.dna.tester.mutantvalidator.service.Imp;

import com.dna.tester.mutantvalidator.model.DNAStat;
import com.dna.tester.mutantvalidator.service.MutantService;
import com.dna.tester.mutantvalidator.service.RedisSyncService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest()
public class StatisticsServiceImpTest {

    @Mock
    private RedisSyncService redisSyncService;

    @InjectMocks
    private StatisticsServiceImp statisticsServiceImp;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveLowerMutantToHumanRatio() {
        String bodyExpected = String.format("{\"count_mutant_dna\": \"%d\", \"count_human_dna\": \"%d\", \"ratio\" : \"%f\"}"
                , 10, 20, 0.5F);
        ResponseEntity responseExpected = new ResponseEntity<>(bodyExpected, HttpStatus.OK);

        DNAStat mutantCountExpected = new DNAStat("Mutant",10L);
        DNAStat humanCountExpected = new DNAStat("Human", 20L);

        when(redisSyncService.find("Mutant")).thenReturn(mutantCountExpected);
        when(redisSyncService.find("Human")).thenReturn(humanCountExpected);
        ResponseEntity result = statisticsServiceImp.retrieveMutantToHumanRatio();

        Assert.assertTrue(result.equals(responseExpected));
    }

    @Test
    public void testRetrieveHigherMutantToHumanRatio() {
        String bodyExpected = String.format("{\"count_mutant_dna\": \"%d\", \"count_human_dna\": \"%d\", \"ratio\" : \"%f\"}"
                , 2000, 400, 5.0F);
        ResponseEntity responseExpected = new ResponseEntity<>(bodyExpected, HttpStatus.OK);

        DNAStat mutantCountExpected = new DNAStat("Mutant",2000L);
        DNAStat humanCountExpected = new DNAStat("Human", 400L);

        when(redisSyncService.find("Mutant")).thenReturn(mutantCountExpected);
        when(redisSyncService.find("Human")).thenReturn(humanCountExpected);
        ResponseEntity result = statisticsServiceImp.retrieveMutantToHumanRatio();

        Assert.assertTrue(result.equals(responseExpected));
    }

    @Test
    public void testRetrieveZeroDivisionMutantToHumanRatio() {
        String bodyExpected = String.format("{\"count_mutant_dna\": \"%d\", \"count_human_dna\": \"%d\", \"ratio\" : \"%f\"}"
                , 50, 0, Float.POSITIVE_INFINITY);
        ResponseEntity responseExpected = new ResponseEntity<>(bodyExpected, HttpStatus.OK);

        DNAStat mutantCountExpected = new DNAStat("Mutant",50L);
        DNAStat humanCountExpected = new DNAStat("Human", 0L);

        when(redisSyncService.find("Mutant")).thenReturn(mutantCountExpected);
        when(redisSyncService.find("Human")).thenReturn(humanCountExpected);
        ResponseEntity result = statisticsServiceImp.retrieveMutantToHumanRatio();

        Assert.assertTrue(result.equals(responseExpected));

    }

    @Test
    public void testRetrieveZeroMutantsMutantToHumanRatio() {
        String bodyExpected = String.format("{\"count_mutant_dna\": \"%d\", \"count_human_dna\": \"%d\", \"ratio\" : \"%f\"}"
                , 0, 100, 0.0F);
        ResponseEntity responseExpected = new ResponseEntity<>(bodyExpected, HttpStatus.OK);

        DNAStat mutantCountExpected = new DNAStat("Mutant",0L);
        DNAStat humanCountExpected = new DNAStat("Human", 100L);

        when(redisSyncService.find("Mutant")).thenReturn(mutantCountExpected);
        when(redisSyncService.find("Human")).thenReturn(humanCountExpected);
        ResponseEntity result = statisticsServiceImp.retrieveMutantToHumanRatio();

        Assert.assertTrue(result.equals(responseExpected));

    }


}