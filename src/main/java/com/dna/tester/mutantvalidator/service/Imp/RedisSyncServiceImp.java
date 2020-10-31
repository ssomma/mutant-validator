package com.dna.tester.mutantvalidator.service.Imp;

import com.dna.tester.mutantvalidator.model.DNA;
import com.dna.tester.mutantvalidator.model.DNAStat;
import com.dna.tester.mutantvalidator.repository.DNARepository;
import com.dna.tester.mutantvalidator.service.RedisSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.List;

@Service
public class RedisSyncServiceImp implements RedisSyncService {

    private static final String CACHE_NAME = "DNASTATS";
    private DNARepository dnaRepository;
    private RedisTemplate<String, BigInteger> redisTemplate;
    private HashOperations<String, String, BigInteger> hashOperations;

    @Autowired
    RedisSyncServiceImp(RedisTemplate<String,BigInteger> redisTemplate, DNARepository dnaRepository) {
        this.redisTemplate = redisTemplate;
        this.dnaRepository = dnaRepository;
    }

    @PostConstruct
    public void initializeHashOperations(){
        hashOperations = redisTemplate.opsForHash();
    }

    @EventListener(ApplicationStartedEvent.class)
    private void syncRedisToDatabase(){
        List<String> mutantResults = dnaRepository.distinctMutantResult();
        for(String result : mutantResults){
            Long valueResult = dnaRepository.countByMutantResultText(result);
            DNAStat dnaStat = new DNAStat(result,valueResult);
            save(dnaStat);
        }
    }

    @Override
    public void save(DNAStat dnaStat) {
        String dnaStatId = dnaStat.getId();
        BigInteger dnaStatResult = BigInteger.valueOf(dnaStat.getResult());
        hashOperations.put(CACHE_NAME, dnaStatId, dnaStatResult);
    }

    @Override
    public void incrStat(String id){
        BigInteger newStat = BigInteger.valueOf(find(id).getResult() + 1);
        hashOperations.put(CACHE_NAME,id,newStat);
    }

    @Override
    public DNAStat find(String id) {
        Long dnaStat = hashOperations.get(CACHE_NAME,id).longValue();
        return new DNAStat(id, dnaStat);
    }


}
