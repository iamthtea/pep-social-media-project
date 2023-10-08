package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** This DAO class mediates the transformation of data between the format of objects in Java to rows in a
 * database. 
 * 
 * This DAO should be able to: 
 * - Add an account to the database, account table
 * - Remove an account from the database, account table?
 * 
 * The Database has a table called "Account", which contains similar values as the Account class:
 * account_id, int, primary key
 * username, varchar(255), unique
 * password, varchar(255)
 **/

public class AccountDAO {
    
    // This method retrieves all accounts in the database
    public List<Account> getAllAccounts(){
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rSet = preparedStatement.executeQuery();
            while(rSet.next()) {
                Account account = new Account(rSet.getInt("account_id"),
                    rSet.getString("username"), 
                    rSet.getString("password"));
                accounts.add(account);
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return accounts;   
    };


    //This method adds a new account into the database and generates an account id.
    public Account addAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pSet = preparedStatement.getGeneratedKeys();
            if (pSet.next()) {
                int generated_account_id = (int) pSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // This method retrieves an account from the database by the inputted username.
    public Account getAccountByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);

            ResultSet rSet = preparedStatement.executeQuery();
            while (rSet.next()) {
                Account account = new Account(rSet.getInt("account_id"), 
                    rSet.getString("username"), 
                    rSet.getString("password"));
                return account;    
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
