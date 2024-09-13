package ui;

import gilts.*;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Driver {
    private static GiltPricingEngine pricingEngine;
    private static List<Gilt> availableGilts;
    private static GiltPortfolio portfolio;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //availableGilts = generateRandomGilts();
        GiltPricingEngine pricer = new MallonGiltPricingEngine(4.46, 4.50, 4.11, 4.23);
        portfolio = new GiltPortfolio(pricer, 10000.00);
        DecimalFormat df = new DecimalFormat("#.##");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

//        Scanner input = new Scanner(System.in);

        // auto close connection
        try (Connection conn = DriverManager
                .getConnection(
                        "jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "postgres"
                )
        ) {
            availableGilts = getAndPrintGilt(conn);
            while (true) {
                do {
                    printMenu();
                    int choice = scanner.nextInt();
                    switch (choice) {
                        case 1:
                            printGilts();
                            break;
                        case 2:
                            System.out.println("Enter coupon");
                            double coupon = scanner.nextDouble();

                            System.out.println("Enter principal");
                            double principal = scanner.nextDouble();

                            System.out.println("Enter yearsRemaining");
                            int yearsRemaining = scanner.nextInt();

                            Gilt gilt = Gilt.create(coupon, principal, yearsRemaining);
                            insertGiltAndPrintNewId(conn, gilt);
                            break;
                        case 3:
                            System.out.println("Enter gilt id");
                            int searchId = scanner.nextInt();
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
                            int tickId = scanner.nextInt();
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
                            int id = scanner.nextInt();

                            System.out.println("Enter new value for coupon");
                            double newCoupon = scanner.nextDouble();

                            System.out.println("Enter new value for principal");
                            double newPrincipal = scanner.nextDouble();

                            System.out.println("Enter new value for yearsRemaining");
                            int newYearsRemaining = scanner.nextInt();

                            Gilt updateGilt = getAndPrintOneGilt(conn, id);

                            if (updateGilt != null) {
                                updateGiltAndPrintGilt(conn, updateGilt, newCoupon, newPrincipal, newYearsRemaining);
                                System.out.println("Gilt after update: " + getAndPrintOneGilt(conn, id));
                            } else {
                                System.out.println("Gilt with ID not found.");
                            }
                            break;
                        case 6:
                            buyGuilt();
                            break;
                        case 7:
                            sellGuilt();
                            break;
                        case 8:
                            viewPortfolio();
                            break;
                        case 9:
                            passTime();
                            break;
                        case 10:
                            System.exit(0);
                        default:
                            break;
                    }
                } while (true);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static List<Gilt> getAndPrintGilt(Connection conn) {
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
//            for (Gilt gilt : result) {
//                System.out.println("gilt: " + gilt);
//            }
        } catch (
                SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void printGilts() {
        for (Gilt gilt : availableGilts) {
            System.out.println(gilt);
        }
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
        availableGilts = getAndPrintGilt(conn);
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

    private static void tickGilt(Connection conn, Gilt tickGilt) {
        int tickGetYearsRemaining = tickGilt.getYearsRemaining() - 1;
        final String SQL_UPDATE = "update gilt set yearsRemaining = ? where id = ?";

        try (PreparedStatement updateStatement = conn.prepareStatement(SQL_UPDATE)) {
            updateStatement.setDouble(1, tickGetYearsRemaining);
            updateStatement.setDouble(2, tickGilt.getId());

            int updatedRows = updateStatement.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Gilt has been ticked successfully.");
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
        availableGilts = getAndPrintGilt(conn);
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
        availableGilts = getAndPrintGilt(conn);
    }

    private static void buyGuilt() {
        Scanner scanner = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("#.##");
        Gilt buyGilt = null;
        for (Gilt g : availableGilts) {
            System.out.println(g);
        }
        System.out.print("Enter id of the gilt to buy: ");
        int id = scanner.nextInt();
        for (Gilt g : availableGilts) {
            if (g.getId() == id) {
                buyGilt = g;
                break;
            }
        }
        if (buyGilt == null) {
            System.out.println("Id was not found, please try again");
        }

        try {
            portfolio.buyGilt(buyGilt);
            System.out.println("Gilt was added to portfolio successfully");
            availableGilts.remove(buyGilt);
            System.out.println("Remaining balance: £" + df.format(portfolio.getBalance()));
        } catch (CantAffordGiltException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void sellGuilt() {
        DecimalFormat df = new DecimalFormat("#.##");
        Scanner scanner = new Scanner(System.in);

        List<Gilt> myPortfolio = portfolio.getPortfolio();
        if (portfolio.getPortfolio().isEmpty()) {
            System.out.println("Portfolio is empty");
            return;
        }
        for (int i = 0; i < myPortfolio.size(); i++) {
            System.out.println("index:" + (i + 1) + ", " + myPortfolio.get(i));
        }

        System.out.print("Enter index of gilt to sell: ");
        int index = scanner.nextInt();
        if (index < 1 || index > myPortfolio.size()) {
            System.out.println("Index is out of range, please try again");
            return;
        }
        Gilt gilt = myPortfolio.get(index - 1);

        try {
            portfolio.sellGilt(gilt);
            System.out.println(gilt + " was sold successfully");
            myPortfolio.remove(gilt);
            System.out.println("Your balance: £" + df.format(portfolio.getBalance()));
        } catch (CantAffordGiltException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewPortfolio() {
        DecimalFormat df = new DecimalFormat("#.##");
        List<Gilt> myPortfolio = portfolio.getPortfolio();

        if (myPortfolio.isEmpty()) {
            System.out.println("Portfolio is empty");
            return;
        }
        for (Gilt gilt : myPortfolio) {
            System.out.println(gilt);
        }
        System.out.println("Balance: £" + df.format(portfolio.getBalance()));
    }

    private static void passTime() {
        portfolio.tick();
        availableGilts = generateRandomGilts();
        System.out.println("Moved onto next year. New gilts are available to buy.");
    }

    private static void printMenu() {
        System.out.println(
                "1. Print Gilts from DB\n" +
                        "2. Insert gilt to DB\n" +
                        "3. Retrieve gilt by ID from DB\n" +
                        "4. Tick gilt by ID in DB\n" +
                        "5. Update gilt in DB\n" +
                        "6. Buy Gilt\n" +
                        "7. Sell Gilt\n" +
                        "8. View Portfolio\n" +
                        "9. Pass Time\n" +
                        "10. Exit\n" +
                        "Enter your choice: ");
    }

    private static List<Gilt> generateRandomGilts() {
        Random rand = new Random();
        List<Gilt> randomGilts = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.##");
        // Generate 5 gilts
        for (int i = 0; i < 5; i++) {
            // Random coupon rate between 0 and 10
            double coupon = Double.valueOf(df.format(rand.nextDouble() * 10));
            // Random principal value between 0 and 1000
            double principal = Double.valueOf(df.format(rand.nextDouble() * 1000));
            // Random years remaining between 1 and 10
            int yearsRemaining = rand.nextInt(20) + 1;

            Gilt randomGilt = Gilt.create(coupon, principal, yearsRemaining);
            randomGilts.add(randomGilt);
        }
        return randomGilts;
    }
}
