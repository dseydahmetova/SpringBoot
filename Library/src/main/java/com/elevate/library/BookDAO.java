package com.elevate.library;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public interface BookDAO {
    Collection<Book> getAllBooks();

    Book getById(Integer id);

    Book addBook(Book book);
}
