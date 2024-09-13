package db;

// THIS IS AN EXAMPLE //
public class Person {
    private Integer id;
    private String fname;
    private String lname;

    public Person(Integer id, String fname, String lname) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
    }
    public Person(String fname, String lname) {
        this(-1, fname, lname);
    }

    public Integer getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                '}';
    }
}
