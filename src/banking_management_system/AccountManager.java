package banking_management_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
    private Connection connection;
    private Scanner sc;

    public AccountManager(Connection con, Scanner sc) {
        this.connection = con;
        this.sc = sc;
    }

    public void credit_money(long account_number) throws SQLException {
        sc.nextLine();
        System.out.print("enter amount to credit :");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.print("enter security pin: ");
        String security_pin = sc.nextLine();
        try {
            connection.setAutoCommit(false);
            if (account_number != 0) {
                String credit_money_query = "SELECT * from accounts WHERE a_number = ? AND security_pin=?;";
                PreparedStatement ps = connection.prepareStatement(credit_money_query);
                ps.setLong(1, account_number);
                ps.setString(2, security_pin);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String credit_query = "UPDATE accounts SET balance = balance + ? WHERE a_number =?;";
                    PreparedStatement ps1 = connection.prepareStatement(credit_query);
                    ps1.setDouble(1, amount);
                    ps1.setLong(2, account_number);
                    int rows_affected = ps1.executeUpdate();
                    if (rows_affected > 0) {
                        System.out.println("Rs." + amount + " credited Successfully");
                        connection.commit();
                        connection.setAutoCommit(true);
                    } else {
                        System.out.println("Transaction Failed!");
                        connection.rollback();
                        connection.setAutoCommit(true);
                    }
                }
            } else {
                System.out.println("invalid Security pin");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public void debit_money(long account_number) throws SQLException{
        sc.nextLine();
        System.out.print("enter amount to credit :");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.print("enter security pin: ");
        String security_pin = sc.nextLine();
        try {
            connection.setAutoCommit(false);
            if (account_number != 0) {
                String credit_money_query = "SELECT * from accounts WHERE a_number = ? AND security_pin=?;";
                PreparedStatement ps = connection.prepareStatement(credit_money_query);
                ps.setLong(1, account_number);
                ps.setString(2, security_pin);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    double current_balance = rs.getDouble("balance");
                    if(current_balance>=amount){
                        String credit_query = "UPDATE accounts SET balance = balance - ? WHERE a_number =?;";
                        PreparedStatement ps1 = connection.prepareStatement(credit_query);
                        ps1.setDouble(1, amount);
                        ps1.setLong(2, account_number);
                        int rows_affected = ps1.executeUpdate();
                        if (rows_affected > 0) {
                            System.out.println("Rs." + amount + " credited Successfully");
                            connection.commit();
                            connection.setAutoCommit(true);
                        } else {
                            System.out.println("Transaction Failed!");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    }
                    else{
                        System.out.println("Insufficient balance!");
                    }
                }
            } else {
                System.out.println("invalid Security pin");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }
    

}
