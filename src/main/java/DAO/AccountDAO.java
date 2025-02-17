package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public Account getAccountByUsername (String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account insertAccount(Account account){
        //insert an account into the Account table
        /*The registration will be successful if and only if the username is not blank, the password is at least 4 characters long, 
        and an Account with that username does not already exist. 
        If all these conditions are met, the response body should contain a JSON of the Account, including its account_id. 
        The new account should be persisted to the database. */

        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    public Account loginAccount (String username, String password) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}