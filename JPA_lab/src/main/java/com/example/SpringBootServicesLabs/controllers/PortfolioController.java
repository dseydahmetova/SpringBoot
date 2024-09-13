package com.example.SpringBootServicesLabs.controllers;
import com.example.SpringBootServicesLabs.entities.Account;
import com.example.SpringBootServicesLabs.entities.Portfolio;
import com.example.SpringBootServicesLabs.services.PortfolioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolios")
@CrossOrigin
public class PortfolioController {

    @Autowired
    private PortfolioService porfolioService;

    @GetMapping
    public List<Portfolio> getAllAccounts() {
        return porfolioService.getAllPortfolios();
    }

    @GetMapping("/{id}")
    public Portfolio getPortfolio(@PathVariable Long id) {
        return porfolioService.getPortfolioById(id);
    }

    @PostMapping
    public ResponseEntity<String> addNewPortfolio(@RequestBody Portfolio portfolio) {
        porfolioService.createPortfolio(portfolio);
        return new ResponseEntity<>("Portfolio successfully added", HttpStatus.CREATED);
    }

}
