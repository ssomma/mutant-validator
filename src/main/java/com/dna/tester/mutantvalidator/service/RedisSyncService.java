package com.dna.tester.mutantvalidator.service;

import com.dna.tester.mutantvalidator.model.DNAStat;
import org.springframework.cache.annotation.CachePut;

import java.util.Map;

public interface RedisSyncService {

   DNAStat find(String id);
   void save(DNAStat dnaStat);
   void incrStat(String id);
}
