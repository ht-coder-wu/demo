package com.example.demo.service;

import com.example.demo.entity.Test;
import com.example.demo.repository.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Service
public class TestService {
    @Resource
    TestRepository testRepository;
    @Transactional
    public Flux<Test> testA(){
        Flux<Test> all = testRepository.findAll();
        return all;
    }
    @Transactional
    public Flux<Test> testB(){
        Test entity = new Test();
        entity.setOid("abc");
        entity.setF1("f1");
        entity.setF2("f2");
        entity.setF3("f3");
        Flux<Test> all = Flux.just(entity);
        return all;
    }
    public Flux<Test>testC(){
        Test entity = new Test();
        entity.setOid("bcd");
        entity.setF1("f1");
        entity.setF2("f2");
        entity.setF3("f3");
        Flux<Test> all = Flux.just(entity);
        return all;
    }
    public Flux<Test> testD(){
        Flux<Test> all = testRepository.findAll();
        return all;
    }
}
