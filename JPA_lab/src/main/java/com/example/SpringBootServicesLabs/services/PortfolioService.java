package com.example.SpringBootServicesLabs.services;

import com.example.SpringBootServicesLabs.entities.Portfolio;

import java.util.List;

public interface PortfolioService {
    List<Portfolio> getAllPortfolios();

    Portfolio createPortfolio(Portfolio portfolio);

    Portfolio getPortfolioById(Long id);
}
