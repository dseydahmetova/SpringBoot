package com.peopleshores.finalProject.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss a")
    private LocalDateTime time;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private Long fromAccount;
    private Integer fromAccountSortCode;
    private Long toAccount;
    private Integer toAccountSortCode;
    private Double amount;
}

