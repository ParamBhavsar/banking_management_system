package banking_management_system;
import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class MainApp {
        private static final String url ="jdbc:mysql://localhost:3306/banking_system";
        private static final String username="root";
        private static final String password ="param1234";
        public static void main(String[] args) throws  ClassNotFoundException {
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
            }
            catch (ClassNotFoundException e){
                System.out.println(e.getMessage());
            }
            try{
                Connection connection = DriverManager.getConnection(url,username,password);
                Scanner sc = new Scanner(System.in);
                Users user = new Users(connection,sc);
                Accounts accounts =new Accounts(connection,sc);
                AccountManager accountManager = new AccountManager(connection,sc);
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
}