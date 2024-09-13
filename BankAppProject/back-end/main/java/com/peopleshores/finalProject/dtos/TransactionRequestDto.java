package com.peopleshores.finalProject.dtos;

import com.peopleshores.finalProject.entities.TransactionType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class TransactionRequestDto{
   private  TransactionType type;
     private Long fromAccount;
     private Integer fromAccountSortCode;
     private Long toAccount;
     private Integer toAccountSortCode;
     private Double amount;
}