package com.peopleshores.finalProject.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RealBankAccountService implements BankAccountService {

    @Value("${sortcode}")
    private Integer sortCode;

    @Override
    public int getAccountSortCode() {
        return sortCode;
    }
}



