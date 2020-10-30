package com.dna.tester.mutantvalidator.model;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("DNAStat")
public class DNAStat implements Serializable {
    private String id;
    private Long value;

    public DNAStat(String id, Long value){
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
