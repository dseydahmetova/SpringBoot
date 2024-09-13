package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


// THIS IS AN EXAMPLE //
public class DriverWithDb {
    static void getAndPrintPeople(Connection conn) {
        final String SQL_SELECT = "select * from person";

        List<Person> result = new ArrayList<>();

        try (PreparedStatement selectStatement = conn.prepareStatement(SQL_SELECT)) {
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {

                Integer id = resultSet.getInt("id");
                String fname = resultSet.getString("fname");
                String lname = resultSet.getString("lname");

                Person p = new Person(id, fname, lname);

                result.add(p);
            }
            for (Person p : result) {
                System.out.println(p);
            }
        } catch (
                SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    static void insertPersonAndPrintNewId(Connection conn, Person p) {
        final String SQL_INSERT = "insert into person (fname, lname) values (?, ?)";

        try (PreparedStatement insertStatement = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            insertStatement.setString(1, p.getFname());
            insertStatement.setString(2, p.getLname());
            int insertedRow = insertStatement.executeUpdate();
            if (insertedRow > 0) {
                var rs = insertStatement.getGeneratedKeys();
                if (rs.next()) {
                    System.out.println(rs.getInt(1));
                }
            }
        } catch (
                SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Scanner input = new Scanner(System.in);

        // auto close connection
        try (Connection conn = DriverManager
                .getConnection(
                        "jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "postgres"
                )
        ) {

            while (true) {
                System.out.println(
                        "1) Print people\n" +
                                "2) Insert person"
                );
                int choice = Integer.parseInt(input.nextLine());
                switch (choice) {
                    case 1:
                        getAndPrintPeople(conn);
                        break;
                    case 2:
                        System.out.println("Enter first name");
                        String fname = input.nextLine();

                        System.out.println("Enter last name");
                        String lname = input.nextLine();

                        Person p = new Person(fname, lname);
                        insertPersonAndPrintNewId(conn, p);
                }
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
