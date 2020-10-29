package com.dna.tester.mutantvalidator.model;

import java.io.Serializable;

public class DNAStat implements Serializable {
    private String key;
    private Long value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public static DNAStatBuilder builder(){return new DNAStatBuilder();}

    public static final class DNAStatBuilder {
        private String key;
        private Long value;

        private DNAStatBuilder() {
        }

        public static DNAStatBuilder aDNAStat() {
            return new DNAStatBuilder();
        }


        public DNAStatBuilder withKey(String key) {
            this.key = key;
            return this;
        }

        public DNAStatBuilder withValue(Long value) {
            this.value = value;
            return this;
        }

        public DNAStat build() {
            DNAStat dNAStat = new DNAStat();
            dNAStat.setKey(key);
            dNAStat.setValue(value);
            return new DNAStat();
        }
    }
}
