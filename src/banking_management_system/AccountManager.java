package banking_management_system;

import java.sql.Connection;
import java.util.Scanner;

public class AccountManager {
    private Connection connection;
    private Scanner sc;
    public AccountManager(Connection con,Scanner sc){
        this.connection=con;
        this.sc=sc;
    }

}
