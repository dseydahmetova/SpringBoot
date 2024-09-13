package com.peopleshores.finalProject.dtos;

import java.util.List;

public record GetCustomerResponseDto (
     Long id,
     String fullName,
     List<Long> accounts){}
