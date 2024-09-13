package com.elevate.library;

public class Patron {
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private int id;
    private String name;
    protected Patron(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
