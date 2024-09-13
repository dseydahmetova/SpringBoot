package db;

import gilts.Gilt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DriverWithDBGilt {

    static void getAndPrintGilt(Connection conn) {
        final String SQL_SELECT = "select * from gilt";
        List<Gilt> result = new ArrayList<>();

        try (PreparedStatement selectStatement = conn.prepareStatement(SQL_SELECT)) {
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                double coupon = resultSet.getDouble("coupon");
                double principal = resultSet.getDouble("principal");
                int yearsRemaining = resultSet.getInt("yearsRemaining");

                Gilt gilt = Gilt.create(coupon, principal, yearsRemaining);
                gilt.setId(id);
                result.add(gilt);
            }
            for (Gilt gilt : result) {
                System.out.println("gilt: " + gilt);
            }
        } catch (
                SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    private static Gilt getAndPrintOneGilt(Connection conn, int searchId) {
        Gilt gilt = null;
        final String SQL_SELECT = "select * from gilt where id = ?";

        try (PreparedStatement selectStatement = conn.prepareStatement(SQL_SELECT)) {
            selectStatement.setInt(1, searchId);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {

                double coupon = resultSet.getDouble("coupon");
                double principal = resultSet.getDouble("principal");
                int yearsRemaining = resultSet.getInt("yearsRemaining");

                gilt = Gilt.create(coupon, principal, yearsRemaining);
                gilt.setId(searchId);

            } else {
                System.out.println("ID was not found");
            }
        } catch (
                SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return gilt;
    }

    static void insertGiltAndPrintNewId(Connection conn, Gilt gilt) {
        final String SQL_INSERT = "insert into gilt (coupon, principal, yearsRemaining) values (?, ?, ?)";

        try (PreparedStatement insertStatement = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            insertStatement.setDouble(1, gilt.getCoupon());
            insertStatement.setDouble(2, gilt.getPrincipal());
            insertStatement.setDouble(3, gilt.getYearsRemaining());
            int insertedRow = insertStatement.executeUpdate();
            if (insertedRow > 0) {
                var rs = insertStatement.getGeneratedKeys();
                if (rs.next()) {
                    gilt.setId(rs.getInt(1));
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

    private static void tickGilt(Connection conn, Gilt tickGilt) {
        int tickGetYearsRemaining = tickGilt.getYearsRemaining() - 1;
        final String SQL_UPDATE = "update gilt set yearsRemaining = ? where id = ?";

        try (PreparedStatement updateStatement = conn.prepareStatement(SQL_UPDATE)) {
//            ResultSet resultSet = updateStatement.executeQuery();
//            if (resultSet.next()) {

//                updateStatement.setDouble(1, tickGilt.getCoupon());
//                updateStatement.setDouble(2, tickGilt.getPrincipal());
//                updateStatement.setInt(3, tickGilt.getYearsRemaining());
            updateStatement.setDouble(1, tickGetYearsRemaining);
            updateStatement.setDouble(2, tickGilt.getId());


            int updatedRows = updateStatement.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Gilt has been ticked successfully.");
            } else {
                System.out.println("ID was not found");
            }
//            }
        } catch (
                SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

    static void updateGiltAndPrintGilt(Connection conn, Gilt gilt, double newCoupon, double newPrincipal, int newYearsRemaining) {
        final String SQL_UPDATE = "update gilt SET coupon = ?, principal = ?, yearsRemaining = ? where id = ?";
        try (PreparedStatement updateStatement = conn.prepareStatement(SQL_UPDATE)) {

            updateStatement.setDouble(1, newCoupon);
            updateStatement.setDouble(2, newPrincipal);
            updateStatement.setDouble(3, newYearsRemaining);
            updateStatement.setDouble(4, gilt.getId());

            int updatedRows = updateStatement.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Gilt was updated successfully.");
            } else {
                System.out.println("ID was not found");
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
                        "1) Print gilt\n" +
                                "2) Insert gilt\n" +
                                "3) Retrieve gilt by ID\n" +
                                "4) Tick gilt by ID\n" +
                                "5) Update gilt"
                );
                int choice = input.nextInt();
                switch (choice) {
                    case 1:
                        getAndPrintGilt(conn);
                        break;

                    case 2:
                        System.out.println("Enter coupon");
                        double coupon = input.nextDouble();

                        System.out.println("Enter principal");
                        double principal = input.nextDouble();

                        System.out.println("Enter yearsRemaining");
                        int yearsRemaining = input.nextInt();

                        Gilt gilt = Gilt.create(coupon, principal, yearsRemaining);
                        insertGiltAndPrintNewId(conn, gilt);
                        break;

                    case 3:
                        System.out.println("Enter gilt id");
                        int searchId = input.nextInt();
                        Gilt searchGilt = getAndPrintOneGilt(conn, searchId);
                        if (searchGilt != null) {
                            System.out.println("Retrieved gilt: " + searchGilt);
                        } else {
                            System.out.println("Gilt with ID " + searchId + " not found.");
                        }
                        break;

                    case 4:
                        getAndPrintGilt(conn);
                        System.out.println("Enter gilt id you want to tick:");
                        int tickId = input.nextInt();
                        Gilt tickGilt = getAndPrintOneGilt(conn, tickId);
                        if (tickGilt != null) {
                            tickGilt(conn, tickGilt);
                            System.out.println("Gilt after tick: " + getAndPrintOneGilt(conn, tickId));
                        } else {
                            System.out.println("Gilt with ID not found.");
                        }
                        break;

                    case 5:
                        getAndPrintGilt(conn);
                        System.out.println("Enter gilt id to update");
                        int id = input.nextInt();

                        System.out.println("Enter new value for coupon");
                        double newCoupon = input.nextDouble();

                        System.out.println("Enter new value for principal");
                        double newPrincipal = input.nextDouble();

                        System.out.println("Enter new value for yearsRemaining");
                        int newYearsRemaining = input.nextInt();

                        Gilt updateGilt = getAndPrintOneGilt(conn, id);

                        if (updateGilt != null) {
                            updateGiltAndPrintGilt(conn, updateGilt, newCoupon, newPrincipal, newYearsRemaining);
                            System.out.println("Gilt after update: " + getAndPrintOneGilt(conn, id));
                        } else {
                            System.out.println("Gilt with ID not found.");
                        }
                        break;
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
