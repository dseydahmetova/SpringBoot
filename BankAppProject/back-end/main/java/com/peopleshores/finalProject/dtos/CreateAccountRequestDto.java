package com.peopleshores.finalProject.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class CreateAccountRequestDto {
    private Long customerId;
    private String accountName;
    private Double openingBalance;
}
