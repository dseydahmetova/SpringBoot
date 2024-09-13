package com.example.demo.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("qa")
public class ExampleServiceQa implements ExampleService{
    public void doSomething() {
        System.out.println("Doing QA stuff...");
    }
}
