package com.example.demo;
import com.example.demo.services.AccountService;
import com.example.demo.services.ExampleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MySpringApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MySpringApplication.class, args);
        ExampleService exampleService = context.getBean(ExampleService.class);
        exampleService.doSomething();
        AccountService accountService = context.getBean(AccountService.class);
        System.out.println(accountService.retrieveAllAccounts());
}
}
