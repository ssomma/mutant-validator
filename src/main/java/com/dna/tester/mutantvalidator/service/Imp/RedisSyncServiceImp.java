package com.dna.tester.mutantvalidator.service.Imp;

import com.dna.tester.mutantvalidator.model.DNAStat;
import com.dna.tester.mutantvalidator.repository.DNARepository;
import com.dna.tester.mutantvalidator.service.RedisSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.List;


@Service
public class RedisSyncServiceImp implements RedisSyncService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private static final String CACHE_NAME = "DNASTATS";

    private DNARepository dnaRepository;
    private final RedisTemplate<String, BigInteger> redisTemplate;
    private HashOperations<String, String, BigInteger> hashOperations;

    @Autowired
    RedisSyncServiceImp(RedisTemplate<String,BigInteger> redisTemplate, DNARepository dnaRepository) {
        log.info("Initializing RedisSyncService.");
        this.redisTemplate = redisTemplate;
        this.dnaRepository = dnaRepository;
    }

    @PostConstruct
    private void initializeHashOperations(){
        log.debug("Getting hash operations.");
        hashOperations = redisTemplate.opsForHash();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void syncRedisToDatabase(){
        log.info("Syncing redis with database");
        List<String> mutantResults = dnaRepository.distinctMutantResult();
        for(String result : mutantResults){
            Long valueResult = dnaRepository.countByMutantResultText(result);
            DNAStat dnaStat = new DNAStat(result,valueResult);
            save(dnaStat);
            log.info(String.format("Synced the stat %s with value %d", result, valueResult));
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
        DNAStat dnaStat = find(id);
        BigInteger newStat = dnaStat != null ? BigInteger.valueOf(dnaStat.getResult() + 1) : BigInteger.valueOf(1);
        hashOperations.put(CACHE_NAME,id,newStat);
    }

    @Override
    public DNAStat find(String id) {
        BigInteger statValue = hashOperations.get(CACHE_NAME, id);
        DNAStat dnaStat = statValue != null ? new DNAStat(id,statValue.longValue()) : null;
        return dnaStat;
    }


}
