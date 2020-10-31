package com.dna.tester.mutantvalidator.model;

import java.lang.Long;

public class DNAStat {

    private String id;
    private Long result;

    public DNAStat(String id, Long result){
        this.id = id;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }
}
