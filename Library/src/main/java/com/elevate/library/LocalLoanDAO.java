package com.elevate.library;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LocalLoanDAO implements LoanDAO{
    private int counter = 0;
    Map<Integer, Loan> loans;
    public LocalLoanDAO() {
        this.loans = new HashMap<>();
    }
    @Override
    public Loan addLoan(Loan loan) {
        int id = ++counter;
        loan = new Loan(id, loan.getBookid(), loan.getPatronid(), loan.isReturned());
        loans.put(id, loan);
        return loan;
    }

    @Override
    public Collection<Loan> getAllLoans() {
        return this.loans.values();
    }

    @Override
    public Loan getById(Integer id) {
        return loans.get(id);
    }

    @Override
    public Loan updateLoan(Loan l) {
        loans.put(l.getId(), l);
        return l;
    }
}
