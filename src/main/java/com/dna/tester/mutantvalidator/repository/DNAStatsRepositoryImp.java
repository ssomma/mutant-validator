/*package com.dna.tester.mutantvalidator.repository;

import com.dna.tester.mutantvalidator.model.DNAStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import java.io.Serializable;
import java.util.Map;

public class DNAStatsRepositoryImp implements Serializable {


    private RedisTemplate<String, DNAStat> redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public DNAStatsRepositoryImp(RedisTemplate<String,DNAStat> redisTemplate){
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(DNAStat dnaStat) {
        hashOperations.put("STAT", dnaStat.getKey(),dnaStat);
    }

    @Override
    public Map<String, DNAStat> findAll() {
        return hashOperations.entries("STAT");
    }

    @Override
    public DNAStat findById(String id) {
        return (DNAStat) hashOperations.get("STAT", id);
    }

    @Override
    public void update(DNAStat dnaStat) {
        save(dnaStat);
    }

    @Override
    public void delete(String id) {
        hashOperations.delete("USER", id);
    }
}
*/