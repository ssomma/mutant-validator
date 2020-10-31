package com.dna.tester.mutantvalidator.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Value("${gene.types}")
    private String geneTypes;

    @Value("${mutant.pattern.length}")
    private int mutantPatternLength;

    public String getGeneTypes() {
        return geneTypes;
    }

    public int getMutantPatternLength() {
        return mutantPatternLength;
    }
}
