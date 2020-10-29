package com.dna.tester.mutantvalidator.utils;

import com.dna.tester.mutantvalidator.exception.InvalidDNASampleException;

public class DNAMutantValidator {

    public static boolean validateAdjacentGene(String[] dna, Character gene, int patternLength, String geneTypes, int rowSize, int columnSize, int initialRow, int initialCol){

        int[] rowDirections = {0,0,1,1,1,-1,-1,-1};
        int[] colDirections = {1,-1,1,0,-1,1,0,-1};
        for(int dirCandidate = 0; dirCandidate < 8; dirCandidate++){

            int cellCandidateRow = initialRow + rowDirections[dirCandidate];
            int cellCandidateCol = initialCol + colDirections[dirCandidate];
            int patternIndex;

            for(patternIndex = 0 ; patternIndex < patternLength; patternIndex++){

                if(cellCandidateRow >= rowSize || cellCandidateCol >= columnSize || cellCandidateRow < 0 || cellCandidateCol < 0) break;
                Character geneValue = dna[cellCandidateRow].charAt(cellCandidateCol);
                validGeneChecker(geneValue, geneTypes);
                if(geneValue != gene) break;

                cellCandidateRow += rowDirections[dirCandidate];
                cellCandidateCol += colDirections[dirCandidate];

            }
            if(patternIndex == patternLength) return true;
        }
        return false;
    }


    // Validadores de datos
    public static void validDNAChecker(String[] dna){
        int rowSize = dna.length;
        int colSize;

        for(int row = 0; row < rowSize; row++){
            colSize = dna[row].length();
            if(rowSize != colSize)
                throw new InvalidDNASampleException(String.format("Invalid Row at %d. DNA Sample should be square.", row));
        }

    }

    public static void validGeneChecker(Character gene, String geneTypes){
        if(!geneTypes.contains(gene.toString())) {
            throw  new InvalidDNASampleException(String.format("Invalid gene. The gene sample %s is not contained on types %s", gene, geneTypes));
        }
    }


}
