/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank;

import java.util.ArrayList;

/**
 *
 * @author Atish
 */
public interface BankControllerInterface {
    ArrayList<Account> ac = new ArrayList();
    public Account findAccount(int accNo);
    public boolean deposit(int accNo, int balance);
    public int withdraw(int accNo, int balance);
    public boolean newAccount(Account a);
    public ArrayList<Account> viewAllAccounts();
    public int delete(int accNo);
    public int fundTransfer(int accNoTarget, int accNoSource, int amount);
}
