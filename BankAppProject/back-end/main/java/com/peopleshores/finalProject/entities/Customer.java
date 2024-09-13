package com.peopleshores.finalProject.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "customers")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String fullName;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<Account> accounts;

    public Customer(String fullName) {
        this.fullName = fullName;
    }
}