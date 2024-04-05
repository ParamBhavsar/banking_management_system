package banking_management_system;

import java.sql.*;
import java.util.Scanner;

public class Accounts {
    private Connection connection;
    private Scanner sc;
    public Accounts(Connection con,Scanner sc){
        this.connection=con;
        this.sc=sc;
    }
    public long open_bank_account(String email){
        if(!account_exist(email)) {
            String open_account_query = "INSERT INTO accounts VALUES(?,?,?,?,?);";
            sc.nextLine();
            System.out.print("enter full name: ");
            String fullname = sc.nextLine();
            System.out.println("enter initial amount: ");
            double balance = sc.nextDouble();
            sc.nextLine();
            System.out.println("enter security pin: ");
            String security_pin = sc.nextLine();

            try {
                long account_number = generate_account_number();
                PreparedStatement ps = connection.prepareStatement(open_account_query);
                ps.setLong(1, account_number);
                ps.setString(2, fullname);
                ps.setString(3, email);
                ps.setDouble(4, balance);
                ps.setString(5, security_pin);
                int rows_affected = ps.executeUpdate();
                if (rows_affected > 0) {
                    return account_number;
                }
            } catch (SQLException e) {
                throw new RuntimeException("Account creation failed...");
            }
        }
        throw new RuntimeException("Account Already Exist");
    }

    private long generate_account_number(){
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT a_number FROM ACCOUNTS ORDER BY a_number DESC LIMIT 1;");
            if(rs.next()){
                long last_acc_number = rs.getLong("a_number");
                return last_acc_number + 1;
            }
            else{
                return 10001000;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return 10001000;
    }

    public long get_Account_number(String email){
        String get_account_no = "SELECT a_number from ACCOUNTS WHERE email=?;";
        try {
            PreparedStatement ps = connection.prepareStatement(get_account_no);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong("a_number");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        throw new RuntimeException("Account Number Doesn't Exist!");
    }

    public boolean account_exist(String email){
        String exist_query ="SELECT a_number FROM ACCOUNTS WHERE email = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(exist_query);
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

}
