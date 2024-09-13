package com.elevate.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;

@Component
public class DBPatronDAO implements PatronDAO {
    @Autowired
    JdbcTemplate template;
    @Override
    public Patron getById(Integer id) {
        Patron p = template.queryForObject(
                "SELECT * FROM libraryservice.patrons WHERE id = ?",
                ((rs, rowNum) -> new Patron(rs.getInt("id"), rs.getString("name"))),
                id);
        return p;

    }

    @Override
    public Collection<Patron> getAllPatrons() {
        return template.query("SELECT * FROM libraryservice.patrons",
                (rs, rowNum) -> (new Patron(rs.getInt("id"), rs.getString("name"))), new Object[] {});
    }

    @Override
    public Patron addPatron(Patron patron) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO libraryservice.patrons (name) VALUES (?)",
                        Statement.RETURN_GENERATED_KEYS); ps.setString(1, patron.getName()); return ps;}
        ,keyHolder);
        return getById((Integer)keyHolder.getKeys().get("id"));
    }
}
