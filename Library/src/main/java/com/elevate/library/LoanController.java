package com.elevate.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class LoanController {
    @Autowired
    BookDAO books;

    @Autowired
    PatronDAO patrons;

    @Autowired
    LoanDAO loans;

    private static class LoanForm {
        public LoanForm() {}
        public LoanForm(int bookid, int patronid) { this.bookid = bookid; this.patronid = patronid;}
        private int bookid;

        public int getBookid() {
            return bookid;
        }

        public int getPatronid() {
            return patronid;
        }

        private int patronid;
    }
    @PostMapping("/loans")
    public Loan newLoan(@RequestBody LoanForm lf) {
        Book book = books.getById(lf.getBookid());
        Patron patron = patrons.getById(lf.getPatronid());
        if (book != null && patron != null) {
            Loan loan = new Loan(0, book.getId(), patron.getId());
            return loans.addLoan(loan);
        }
        return null;
    }

    @GetMapping("/loans")
    public Collection<Loan> getAllLoans() {
        return loans.getAllLoans();
    }

    @GetMapping("/loans/{id}")
    public Loan getLoan(@PathVariable Integer id) {
        return loans.getById(id);
    }

    @PutMapping("/loans/{id}/return")
    public Loan returnLoan(@PathVariable Integer id) {
        Loan l = loans.getById(id);
        l.setReturned(true);
        return loans.updateLoan(l);
    }

}
