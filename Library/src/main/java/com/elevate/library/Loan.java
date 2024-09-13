package com.elevate.library;

public class Loan {
    private int bookid;


    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public int getId() {
        return id;
    }

    public int getBookid() {
        return bookid;
    }

    public int getPatronid() {
        return patronid;
    }

    private int patronid;
    private int id;

    public boolean isReturned() {
        return returned;
    }

    private boolean returned;

    protected Loan(int id, int book, int patron, boolean returned) {
        this.bookid = book;
        this.id = id;
        this.patronid = patron;
        this.returned = returned;
    }

    protected Loan(int id, int book, int patron) {
        this(id, book, patron, false);
    }
}
