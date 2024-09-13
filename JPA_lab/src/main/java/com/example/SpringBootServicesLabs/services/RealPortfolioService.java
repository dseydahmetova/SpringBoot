package com.example.SpringBootServicesLabs.services;

import com.example.SpringBootServicesLabs.entities.Portfolio;
import com.example.SpringBootServicesLabs.repo.JpaPortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RealPortfolioService implements PortfolioService{
    private final JpaPortfolioRepository portfolioRepository;

    @Autowired
    public RealPortfolioService(JpaPortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }
    
    @Override
    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    @Override
    public Portfolio createPortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);

    }

    @Override
    public Portfolio getPortfolioById(Long id) {
        return portfolioRepository.findById(id).orElse(null);
    }
}
