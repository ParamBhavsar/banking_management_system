package banking_management_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Users {
    private Connection connection;
    private Scanner sc;

    public Users(Connection con , Scanner sc){
        this.connection =con;
        this.sc=sc;
    }

    public void register(){
        sc.nextLine();
        System.out.print("full name: " );
        String fullname= sc.nextLine();
        System.out.print("email: ");
        String email=sc.nextLine();
        System.out.print("password (upto 8 char) : ");
        String password = sc.nextLine();
        if(user_exist(email)){
            System.out.print("user already exists");
            return;
        }
        String reg_query="INSERT INTO USERS VALUES(?,?,?)";
        try{
            PreparedStatement ps = connection.prepareStatement(reg_query);
            ps.setString(1,fullname);
            ps.setString(2,email);
            ps.setString(3,password);
            int affected_rows = ps.executeUpdate();
            if(affected_rows > 0){
                System.out.println("User successfully registered");
            }
            else{
                System.out.println("registration failed");
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public String login(){
        sc.nextLine();
        System.out.print("enter your email :");
        String email = sc.nextLine();
        System.out.print("enter your password :");
        String password = sc.nextLine();
        String login_query = "SELECT * FROM USERS WHERE email=? AND password=?";
        try{
            PreparedStatement ps = connection.prepareStatement(login_query);
            ps.setString(1,email);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return email;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public boolean user_exist(String email){
        try {
            String exist_query = "SELECT * from users where email =?";
            PreparedStatement ps = connection.prepareStatement(exist_query);
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
