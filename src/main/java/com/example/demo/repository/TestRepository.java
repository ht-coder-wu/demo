package com.example.demo.repository;

import com.example.demo.entity.Test;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends ReactiveCrudRepository<Test,String> {
}
