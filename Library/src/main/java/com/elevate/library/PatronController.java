package com.elevate.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class PatronController {
    @Autowired
    private PatronDAO patrons;

    @GetMapping(value="/")
    public String index() {
        return "Welcome to the Elevate RESTful Library";
    }

    @GetMapping(value="/patrons/{id}")
    public Patron getPatron(@PathVariable Integer id) {
        return patrons.getById(id);
    }

    @GetMapping(value="/patrons")
    public Collection<Patron> getAllPatrons() {
        return patrons.getAllPatrons();
    }

    @PostMapping(value="/patrons")
    @ResponseStatus(HttpStatus.CREATED)
    public Patron newPatron(@RequestBody Patron patron) {
        return patrons.addPatron(patron);
    }

}
