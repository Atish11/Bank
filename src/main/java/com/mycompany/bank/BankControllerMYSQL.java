/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Atish
 */
public class BankControllerMYSQL implements BankControllerInterface{
    dbConnection dbConn;

    public BankControllerMYSQL() {
        dbConn = new dbConnection();
    }
    
    
    @Override
    public Account findAccount(int accNo) {
        ResultSet rs;
        Account a;
        String sqlStmt = "SELECT * FROM account WHERE accNo = '"+accNo+"';";
        rs = dbConn.select(sqlStmt);
        try{
            while(rs.next()){
                a = new Account(rs.getInt("accNo"), rs.getString("name"), rs.getInt("amount"), rs.getString("accType").charAt(0));
                return a;
            }
        }catch (SQLException e){
            ;
        }
        return null;
    }

    @Override
    public boolean deposit(int accNo, int balance) {
        Account a = findAccount(accNo);
        if(a != null)
        {
            a.setAmount(a.getAmount()+balance);
            String sqlStmt = "UPDATE `bank`.`account` SET `amount` = '"+a.getAmount()+"' WHERE (`accNo` = '"+accNo+"');";
            return dbConn.iud(sqlStmt)>0;
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public int withdraw(int accNo, int balance) {
        Account a = findAccount(accNo);
        if(a != null)
        {
             if(a.getAmount()<balance)
             {
               return -1;
             }
            a.setAmount(a.getAmount()-balance);
            String sqlStmt = "UPDATE `bank`.`account` SET `amount` = '"+a.getAmount()+"' WHERE (`accNo` = '"+accNo+"');";
            if(dbConn.iud(sqlStmt)>0)
            {
                return 1;
            }
            else
            {
                return -2;
            }
        }
        else
        {
            return 0;
        }
    }

    @Override
    public boolean newAccount(Account a) {
        String sqlStmt = "INSERT INTO `bank`.`account` (`accNo`, `name`, `amount`, `accType`) VALUES ('"+a.getAccNo()+"', '"+a.getName()+"', '"+a.getAmount()+"', '"+a.getAccType()+"');";
        if(dbConn.iud(sqlStmt)>0){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public ArrayList<Account> viewAllAccounts() {
        ResultSet rs;
        String sqlStmt = "SELECT * FROM account;";
        ac.clear();
        rs = dbConn.select(sqlStmt);
        try{
            while(rs.next()){
                Account a = new Account(rs.getInt("accNo"), rs.getString("name"), rs.getInt("amount"), rs.getString("accType").charAt(0));
                ac.add(a);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ac;
    }

    @Override
    public int delete(int accNo) 
    {

        Account a = findAccount(accNo);
        if (a != null) 
        {
            if (a.getAmount() == 0) 
            {
                String sqlStmt = "DELETE FROM `bank`.`account` WHERE (`accNo` = '" + accNo + "');";
                return dbConn.iud(sqlStmt);
            }
            else
            {
                return -2;
            }
        } 
        else 
        {
            return 0;
        }
    }

    @Override
    public int fundTransfer(int accNoTarget, int accNoSource, int amount) {
        Account a = findAccount(accNoSource);
        if(a == null)
        {
            return -1;
        }
        Account b = findAccount(accNoTarget);
        if(b == null)
        {
            return -2;
        }
        if(a.getAmount() < amount)
        {
            return -3;
        }
        a.setAmount(a.getAmount() - amount);
        b.setAmount(b.getAmount() + amount);
        String sqlStmtA ="UPDATE `bank`.`account` SET `amount` = '"+a.getAmount()+"' WHERE (`accNo` = '"+a.getAccNo()+"');";
        String sqlStmtB ="UPDATE `bank`.`account` SET `amount` = '"+b.getAmount()+"' WHERE (`accNo` = '"+b.getAccNo()+"');";
        if((dbConn.iud(sqlStmtA)==1) && (dbConn.iud(sqlStmtB)==1))
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    
}
