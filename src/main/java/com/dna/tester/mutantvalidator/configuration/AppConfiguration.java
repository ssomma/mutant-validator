package com.dna.tester.mutantvalidator.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Value("${gene.types}")
    private static String geneTypes;

    @Value("${mutant.pattern.length}")
    private static int mutantPatternLength;

    public static String getGeneTypes() {
        return geneTypes;
    }

    public static int getMutantPatternLength() {
        return mutantPatternLength;
    }
}
