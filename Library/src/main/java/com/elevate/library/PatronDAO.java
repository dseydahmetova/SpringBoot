package com.elevate.library;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public interface PatronDAO {
    Patron getById(Integer id);

    Collection<Patron> getAllPatrons();

    Patron addPatron(Patron patron);
}
