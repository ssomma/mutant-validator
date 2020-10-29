package com.dna.tester.mutantvalidator.model;

import java.util.Objects;

public class DNACandidateDTO {


    private String[] dna;


    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DNACandidateDTO that = (DNACandidateDTO) o;
        return Objects.equals(dna, that.dna);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dna);
    }

    @Override
    public String toString() {
        return "DNACandidateDTO{" +
                "dna=" + dna +
                '}';
    }
}
