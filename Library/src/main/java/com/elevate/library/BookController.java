package com.elevate.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class BookController {
    @Autowired
    BookDAO books;

    @GetMapping("/books")
    public Collection<Book> getAllBooks() {
        return books.getAllBooks();
    }

    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable Integer id) {
        return books.getById(id);
    }

    @PostMapping(value="/books")
    public Book newPatron(@RequestBody Book book) {
        return books.addBook(book);
    }


}
