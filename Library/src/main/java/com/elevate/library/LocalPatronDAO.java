package com.elevate.library;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class LocalPatronDAO implements PatronDAO {
    private int counter = 0;
    Map<Integer, Patron> patrons;
    public LocalPatronDAO() {
        patrons = new HashMap<>();
    }

    @Override
    public Patron getById(Integer id) {
        return patrons.get(id);
    }

    @Override
    public Collection<Patron> getAllPatrons() {
        return patrons.values();
    }

    @Override
    public Patron addPatron(Patron patron) {
        patron = new Patron(++counter, patron.getName());
        this.patrons.put(counter, patron);
        return patron;
    }
}
