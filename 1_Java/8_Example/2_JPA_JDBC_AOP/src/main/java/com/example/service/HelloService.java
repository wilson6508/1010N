package com.example.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface HelloService {
    String hello();
}
