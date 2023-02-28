package com.example.demo.controller;

import com.example.demo.entity.Test;
import com.example.demo.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
public class TestController {
    @Resource
    TestService testService;
    @RequestMapping("testA")
    public Flux<Test> testA(){
        return testService.testA();
    }
    @RequestMapping("testB")
    public Flux<Test> testB(){
        return testService.testB();
    }
    @RequestMapping("testC")
    public Flux<Test> testC(){
        return testService.testC();
    }
    @RequestMapping("testD")
    public Flux<Test> testD(){
        return testService.testD();
    }
}
