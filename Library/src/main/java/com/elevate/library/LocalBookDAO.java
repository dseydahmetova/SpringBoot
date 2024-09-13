package com.elevate.library;

import org.springframework.cglib.core.Local;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LocalBookDAO implements BookDAO{
    private int counter = 0;
    private Map<Integer, Book> books;

    public LocalBookDAO() {
        books = new HashMap<>();
    }
    @Override
    public Collection<Book> getAllBooks() {
        return books.values();
    }

    @Override
    public Book getById(Integer id) {
        return books.get(id);
    }

    @Override
    public Book addBook(Book book) {
        int id = ++counter;
        book = new Book(id, book.getTitle(), book.getAuthor());
        books.put(id, book);
        return book;
    }
}
