package com.example.SpringBootServicesLabs.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Portfolio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="portfolio_id")
    private List<Trade> trades = new ArrayList<>();

    public Portfolio(String name) {
        this.name = name;
    }

    public Portfolio(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public void setTrades(List<Trade> trades) {
        this.trades = trades;
    }

    @Override
    public String toString() {
        return "Porfolio{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", trades=" + trades +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Portfolio porfolio = (Portfolio) o;
        return Objects.equals(id, porfolio.id) && Objects.equals(name, porfolio.name) && Objects.equals(trades, porfolio.trades);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, trades);
    }
}
