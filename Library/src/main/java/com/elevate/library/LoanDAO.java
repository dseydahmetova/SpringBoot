package com.elevate.library;

import java.util.Collection;

public interface LoanDAO {
    Loan addLoan(Loan loan);

    Collection<Loan> getAllLoans();

    Loan getById(Integer id);

    Loan updateLoan(Loan l);
}
