package com.dna.tester.mutantvalidator.model;

import java.util.Arrays;
import java.util.Objects;

public class DNACandidateDTO {


    private String[] dna;


    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }

    public static DNACandidateDTOBuilder builder(){
        return new DNACandidateDTOBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DNACandidateDTO that = (DNACandidateDTO) o;
        return Arrays.equals(dna, that.dna);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dna);
    }

    @Override
    public String toString() {
        return "DNACandidateDTO{" +
                "dna=" + Arrays.toString(dna) +
                '}';
    }

    public static final class DNACandidateDTOBuilder {
        private String[] dna;

        private DNACandidateDTOBuilder() {
        }

        public static DNACandidateDTOBuilder aDNACandidateDTO() {
            return new DNACandidateDTOBuilder();
        }

        public DNACandidateDTOBuilder withDna(String[] dna) {
            this.dna = dna;
            return this;
        }

        public DNACandidateDTO build() {
            DNACandidateDTO dNACandidateDTO = new DNACandidateDTO();
            dNACandidateDTO.setDna(dna);
            return dNACandidateDTO;
        }
    }
}
