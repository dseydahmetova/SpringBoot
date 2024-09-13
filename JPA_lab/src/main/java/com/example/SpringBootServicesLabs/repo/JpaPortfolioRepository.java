package com.example.SpringBootServicesLabs.repo;

import com.example.SpringBootServicesLabs.entities.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPortfolioRepository extends JpaRepository<Portfolio, Long> {
}
