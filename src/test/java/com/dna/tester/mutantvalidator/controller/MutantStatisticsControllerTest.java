package com.dna.tester.mutantvalidator.controller;

import com.dna.tester.mutantvalidator.model.DNACandidateDTO;
import com.dna.tester.mutantvalidator.service.MutantService;
import com.dna.tester.mutantvalidator.service.StatisticsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

public class MutantStatisticsControllerTest {

    @InjectMocks
    private MutantStatisticsController mutantStatisticsController;


    @Mock
    private StatisticsService statisticsService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testReturnsExpectedBody() {
        String bodyExpected = String.format("{\"count_mutant_dna\": \"%d\", \"count_human_dna\": \"%d\", \"ratio\" : \"%f\"}"
                , 12000, 12, 1200.0F);
        ResponseEntity responseExpected = new ResponseEntity<>(bodyExpected, HttpStatus.OK);

        doReturn(responseExpected).when(statisticsService).retrieveMutantToHumanRatio();
        ResponseEntity actualResponse = mutantStatisticsController.getDNAStats();
        Assert.assertEquals(responseExpected,actualResponse);

    }
}