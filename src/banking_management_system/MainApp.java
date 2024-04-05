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

                String email;
                long account_number;

                while(true){
                    System.out.println("********** WELCOME TO BANKING MANAGEMENT SYSTEM **********");
                    System.out.println("enter your choice :");
                    System.out.println("1.Register");
                    System.out.println("2.Login");
                    System.out.println("3.exit");
                    int choice1 = sc.nextInt();
                    switch (choice1){
                        case 1:
                            user.register();
                            break;
                        case 2:
                            email = user.login();
                            if(email!=null){
                                System.out.println();
                                System.out.println("User logged in...");
                                if(!accounts.account_exist(email)){
                                    System.out.println("1. Open new bank account");
                                    System.out.println("2. Exit");
                                    int ans = sc.nextInt();
                                    if(ans==1){
                                        account_number = accounts.open_bank_account(email);
                                        System.out.println("Account created successfully");
                                        System.out.println("your account number is "+account_number);
                                    }else {
                                        break;
                                    }
                                }
                                account_number=accounts.get_Account_number(email);
                                int choice2 = 0;
                                while(choice2 != 5){
                                    System.out.println();
                                    System.out.println("1. Debit Money");
                                    System.out.println("2. Credit Money");
                                    System.out.println("3. Transfer Money");
                                    System.out.println("4. Check Balance");
                                    System.out.println("5. Log Out");
                                    System.out.println("Enter your choice: ");
                                    choice2 = sc.nextInt();
                                    switch (choice2){
                                        case 1:
                                            accountManager.debit_money(account_number);
                                            break;
                                        case 2:
                                            accountManager.credit_money(account_number);
                                            break;
                                        case 3:
                                            accountManager.transfer_money(account_number);
                                            break;
                                        case 4:
                                            accountManager.get_balance(account_number);
                                            break;
                                        case 5:
                                            break;
                                        default:
                                            System.out.println("invalid choice.");
                                            break;
                                    }
                                }

                            }
                            else{
                                System.out.println("incorrect email or password.");
                            }
                            break;
                        case 3:
                            System.out.println("Thanks for using banking system");
                            System.out.println("Exiting system...");
                            return;
                        default:
                            System.out.println("enter valid choice ");
                            break;
                    }

                }
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
}