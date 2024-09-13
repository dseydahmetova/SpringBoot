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
public class DBBookDAO implements BookDAO{
    @Autowired
    JdbcTemplate template;
    @Override
    public Collection<Book> getAllBooks() {
        return template.query("SELECT * FROM libraryservice.books ",
                ((rs, rowNum) -> new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"))
                ));
    }

    @Override
    public Book getById(Integer id) {
        Book b = template.queryForObject(
                "SELECT * FROM libraryservice.books where id = ?",
                ((rs, rowNum) -> new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"))),
                id
        );
        return b;
    }

    @Override
    public Book addBook(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO libraryservice.books (title, author) VALUES (?, ?)",
                            Statement.RETURN_GENERATED_KEYS); ps.setString(1, book.getTitle()); ps.setString(2, book.getAuthor()); return ps;}
                ,keyHolder);
        return getById((Integer)keyHolder.getKeys().get("id"));
    }
}
