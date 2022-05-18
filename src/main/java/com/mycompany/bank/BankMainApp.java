/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank;

import java.util.Scanner;

/**
 *
 * @author Atish
 */

//This app should let the teller staff to perform following operations on the account.
    // 1. Create new account.
    // 2. Withdraw from account.
    // 3. Deposit into Account.
    // 4. Display Account information.

//This is called view

public class BankMainApp {

    dbConnection dbConn = new dbConnection();
    
    Scanner sc = new Scanner(System.in);
    String name;
    int amount;
    int accNo;
    char accType, ans;
    int bal;
    int accNoSrc, accNoTrgt;
    BankControllerInterface bc = new BankControllerMYSQL();
    

    public void viewAllAccounts() {
        try {
            for (Account a : bc.viewAllAccounts()) {
                System.out.println(a.toString());
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Error occured");
        }
    }

    public void newAccount() {

        System.out.println("Account No: ");
        try
        {
            accNo = sc.nextInt();
        }
        catch(Exception n)
        {
            System.out.println("Invalid Account Number..");
        }
        sc.nextLine();
        
        System.out.println("Name: ");
        try
        {
            name = sc.nextLine();
        }
        catch(Exception n)
        {
            System.out.println("Invalid Name..");
        }
        
        System.out.println("Opening Balance: ");
        try 
        {
            amount = sc.nextInt();
        } 
        catch (Exception n) 
        {
            amount = 0;
            System.out.println("The input is not a number.");
        }
        sc.nextLine();
        
        System.out.println("Account Type ('C' for Current; 'F' for Fixed; 'S' for Saving): ");
        try 
        {
            accType = sc.nextLine().charAt(0);
        } 
        catch (Exception n) 
        {
            System.out.println("Invalid Account Type...");
        }
       
        if (bc.newAccount(new Account(accNo, name, amount, accType))) 
        {
            System.out.println("Account Created successfully.");
        }
        else
        {
            System.out.println("Cannot create account.");
        }
    }


    public void withdraw() {
        System.out.println("Enter Account Number: ");
        try 
        {
            accNo = sc.nextInt();
        } 
        catch (Exception n) 
        {
            System.out.println("Invalid Account Number...");
        }
        sc.nextLine();
        System.out.println("Enter amount to withdraw: ");
        try 
        {
            bal = sc.nextInt();
        } 
        catch (Exception n) 
        {
            System.out.println("Invalid Ammount...");
        }
        
        int status = bc.withdraw(accNo, bal);
        switch(status)
        {
            case 1:
                System.out.println("Withdraw sucessful.");
                break;
            case -2:
                System.out.println("Update function in database didn't go well..");
                break;
            case -1:
                System.out.println("Insufficient balance.");
                break;
            case 0:
                System.out.println("Account not found");
            
        }

    }

    public void deposit() {
        System.out.println("Enter Account Number: ");
        try 
        {
            accNo = sc.nextInt();
        } 
        catch (Exception n) 
        {
            System.out.println("Invalid Account Number...");
        }
        sc.nextLine();
        System.out.println("Enter amount to Deposite: ");
        try 
        {
            bal = sc.nextInt();
        } 
        catch (Exception n) 
        {
            bal = 0;
            System.out.println("Invalid Amount...");
        }
        
        if(bc.deposit(accNo, bal))
        {
            System.out.println("The Deposit Successful. New Balance is "+bc.findAccount(accNo).getAmount());
        }
        else
        {
            System.out.println("Account not existent!!");
        }

    }
    
    public void delete() {
        System.out.println("Enter Account Number: ");
        try 
        {
            accNo = sc.nextInt();
        } 
        catch (Exception n) 
        {
            System.out.println("Invalid Account Number...");
        }
        sc.nextLine();
        int status = bc.delete(accNo);
        switch(status)
        {
            case 1:
                System.out.println("Account Delete sucessful.");
                break;
            case -2:
                System.out.println("Account Balance have some money, please withdraw first.");
                System.out.println("");
                System.out.println("Do you want to withdraw money and then delete your account.");
                System.out.println("Type 'Y' for yes and 'N' for no..");
                try 
                {
                    ans = sc.nextLine().charAt(0);
                } 
                catch (Exception n) 
                {
                    System.out.println("Invalid Answer...");
                }
                if(ans == 'Y' || ans == 'y')
                {
                    bc.withdraw(accNo, bc.findAccount(accNo).getAmount());
                    bc.delete(accNo);
                    System.out.println("Account is deleted Succesfully");
                }
                else
                {
                    System.out.println("Account is not deleted.");
                    break;
                   
                }
                break;

            case -1:
                System.out.println("Delete function in database didn't go well..");
                break;
            case 0:
                System.out.println("Account not found");
            
        }
    }
    
        
    public void fundTransfer() {
        System.out.println("Enter Source Account Number: ");
        try 
        {
            accNoSrc = sc.nextInt();
        } 
        catch (Exception n) 
        {
            System.out.println("Invalid Account Number...");
        }
        sc.nextLine();
        System.out.println("Enter Target Account Number: ");
        try 
        {
            accNoTrgt = sc.nextInt();
        } 
        catch (Exception n) 
        {
            System.out.println("Invalid Account Number...");
        }
        sc.nextLine();
        System.out.println("Enter Ammount to transfer: ");
        try 
        {
            bal = 0;
            bal = sc.nextInt();
        } 
        catch (Exception n) 
        {
            System.out.println("Invalid Amount...");
        }

        int status = bc.fundTransfer(accNoTrgt, accNoSrc, bal);
        switch (status) 
        {
            case 1:
                System.out.println("A Funds Transferred sucessful.");
                break;
            case -1:
                System.out.println("Source account dosn't exists.");
            case -2:
                System.out.println("Target account dosn't exists.");
                break;
            case -3:
                System.out.println("The source fund doesn't have the required balance.");
                break;
            case 0:
                System.out.println("Fund Transfer function in database didn't go well..");
                break;
        }
    }

    public void displayMenu() {
        System.out.println("=============================================");
        System.out.println("\t\tCore Banking System");
        System.out.println("=============================================");
        System.out.println("1. Create new Account");
        System.out.println("2. Withdraw money from Account");
        System.out.println("3. Deposit money into Account");
        System.out.println("4. Display All Account information");
        System.out.println("5. Delete an Account");
        System.out.println("6. Inter Account Fund Transfer");
        System.out.println("0. Exit");
    }

    public static void main(String[] args) {
        int choice;
        Scanner sc;
        BankMainApp bma = new BankMainApp();
        do {
            bma.displayMenu();
            System.out.println("Enter your choice (0-4): ");
        try 
        {
            sc = new Scanner(System.in);
            choice = sc.nextInt();

            switch (choice) 
            {
                case 1:
                    bma.newAccount();
                    break;
                case 2:
                    bma.withdraw();
                    break;
                case 3:
                    bma.deposit();
                    break;
                case 4:
                    bma.viewAllAccounts();
                    break;
                case 5:
                    bma.delete();
                    break;
                case 6:
                    bma.fundTransfer();
                    break;
                case 0:
                    System.out.println("Exiting");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        } 
        catch (Exception n) 
        {
            System.out.println("Invalid Choice...");
        } 
        } while (true);
    }
}
