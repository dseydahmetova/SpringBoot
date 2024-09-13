package com.elevate.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;

public class DBLoanDAO implements LoanDAO{
    @Autowired
    JdbcTemplate template;
    @Override
    public Loan addLoan(Loan loan) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO libraryservice.loans (book_id, patron_id, returned) VALUES (?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, loan.getBookid());
                    ps.setInt(2, loan.getPatronid());
                    ps.setBoolean(3, loan.isReturned());
                    return ps;}
                ,keyHolder);
        return getById((Integer)keyHolder.getKeys().get("id"));
    }

    @Override
    public Collection<Loan> getAllLoans() {
        return template.query("SELECT * FROM libraryservice.loans",
                (rs, rowNum) -> new Loan(rs.getInt("id"), rs.getInt("book_id"), rs.getInt("patron_id"),rs.getBoolean("returned")));

    }

    @Override
    public Loan getById(Integer id) {
        return template.queryForObject("SELECT * FROM libraryservice.loans WHERE id = ?",
                (rs, rowNum) -> new Loan(rs.getInt("id"), rs.getInt("book_id"), rs.getInt("patron_id"),rs.getBoolean("returned")),
                        id);
    }

    @Override
    public Loan updateLoan(Loan loan) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement("UPDATE libraryservice.loans SET book_id = ?, patron_id = ?, returned = ? WHERE id = ?",
                            Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, loan.getBookid());
                    ps.setInt(2, loan.getPatronid());
                    ps.setBoolean(3, loan.isReturned());
                    ps.setInt(4, loan.getId());
                    return ps;}
                ,keyHolder);
        return getById((Integer)keyHolder.getKeys().get("id"));
    }
}
