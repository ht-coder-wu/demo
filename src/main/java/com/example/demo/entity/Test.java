package com.example.demo.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Data
public class Test {
    private String oid;
    private String f1;
    private String f2;
    private String f3;
}
