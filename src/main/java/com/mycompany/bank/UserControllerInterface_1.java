/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bank;

/**
 *
 * @author Atish
 */
public interface UserControllerInterface_1 {
    public boolean verifyUser(User u);
    public boolean banUser(User u);
    public boolean unbanUser(User u);
    public User searchUserByUname (String uname);
    public User searchUserByUid (String uid);
    public boolean createUser(User u);
}
