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
        System.out.print("enter amount to debit :");
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
                            System.out.println("Rs." + amount + " debited Successfully");
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

    public void transfer_money(long sender_account_number) throws SQLException{
        sc.nextLine();
        System.out.println("enter receiver's account number: ");
        Long receiver_account_number = sc.nextLong();
        System.out.println("Enter the amount you want to transfer");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.println("enter security pin:");
        String security_pin = sc.nextLine();

        try{
            connection.setAutoCommit(false);
            if(sender_account_number !=0 && receiver_account_number!=0){
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM ACCOUNTS WHERE a_number = ? AND security_pin = ?;");
                ps.setLong(1,sender_account_number);
                ps.setString(2,security_pin);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    double current_balance = rs.getDouble("balance");
                    if(current_balance>=amount){
                        String debitQuery = "UPDATE accounts SET balance = balance - ? WHERE a_number = ?;";
                        String creditQuery = "UPDATE accounts SET balance = balance + ? WHERE a_number = ?;";

                        PreparedStatement debit_ps = connection.prepareStatement(debitQuery);
                        PreparedStatement credit_ps = connection.prepareStatement(creditQuery);

                        debit_ps.setDouble(1,amount);
                        debit_ps.setLong(2,sender_account_number);

                        credit_ps.setDouble(1,amount);
                        credit_ps.setLong(2,receiver_account_number);

                        int rows_affected = debit_ps.executeUpdate();
                        int rows_affected2 = credit_ps.executeUpdate();

                        if(rows_affected>0 && rows_affected2 >0){
                            System.out.println("transaction successfull ");
                            System.out.println("Rs " + amount + " transferred successfully");
                            connection.commit();
                            connection.setAutoCommit(true);
                            return;
                        }
                        else{
                            System.out.println("transaction failed");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    }
                    else{
                        System.out.println("insufficient balance");
                    }
                }
                else{
                    System.out.println("Invalid security pin");
                }
            }
            else{
                System.out.println("Invalid account number");
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        connection.setAutoCommit(true);
    }

    public void get_balance(long account_number){
        sc.nextLine();
        System.out.println("enter security pin");
        String security_pin = sc.nextLine();
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT balance from accounts WHERE a_number = ? AND security_pin = ? ;");
            ps.setLong(1,account_number);
            ps.setString(2,security_pin);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                double balance = rs.getDouble("balance");
                System.out.println("Balance is : " + balance + "Rs");
            }
            else{
                System.out.println("invalid security pin");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
