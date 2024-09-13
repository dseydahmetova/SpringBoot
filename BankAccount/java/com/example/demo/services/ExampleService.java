package com.example.demo.services;

public interface ExampleService {
    default void doSomething(){
        System.out.println("Hello, World");
    }
}
