/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 *
 * @author Atish
 */
public class UserController_1 implements UserControllerInterface{
    dbConnection dbConn;

    public UserController_1() {
        dbConn = new dbConnection();
    }
    
    

    @Override
    public boolean verifyUser(User u) {
        
        User uReal = searchUserByUname(u.getUname());
        if(uReal == null)
        {
            return false;
        }
        if(uReal.getUname().equals(u.getUname()) && BCrypt.checkpw(u.getUpassword(), uReal.getUpassword()))
        {
            return true;
        }
        else
        {
        return false;
        }
    }

    @Override
    public boolean banUser(User u) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean unbanUser(User u) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User searchUserByUname(String uname) {
        User u;
        String sqlStmt = "SELECT *FROM user WHERE uname = '"+uname+"';";
        ResultSet rs = dbConn.select(sqlStmt);
        
        try {
            while(rs.next())
            {
                u = new User(rs.getInt("uid"), rs.getString("uname"), rs.getString("upassword"));
                return u;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController_1.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }

    @Override
    public User searchUserByUid(String uid) {
        User u;
        String sqlStmt = "SELECT *FROM user WHERE uid = '"+uid+"';";
        ResultSet rs = dbConn.select(sqlStmt);
        
        try {
            while(rs.next())
            {
                u = new User(rs.getInt("uid"), rs.getString("uname"), rs.getString("upassword"));
                return u;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController_1.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return null;
    }

    @Override
    public boolean createUser(User u) {
        String sqlStmt = "INSERT INTO `bank`.`user` (`uid`, `uname`, `upassword`) VALUES ('"+u.getUid()+"', '"+u.getUname()+"', '"+u.getUpassword()+"');";
        return (dbConn.iud(sqlStmt)>0);
    }
    
}
