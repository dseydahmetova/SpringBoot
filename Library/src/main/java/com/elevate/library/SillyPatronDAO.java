package com.elevate.library;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class SillyPatronDAO implements PatronDAO {


    @Override
    public Patron getById(Integer id) {
        return new Patron(id, "Honk");
    }

    @Override
    public Collection<Patron> getAllPatrons() {
        return null;
    }

    @Override
    public Patron addPatron(Patron patron) {
        return new Patron(9999999, "Honk!!");
    }
}
