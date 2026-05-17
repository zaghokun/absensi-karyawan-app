/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package absensi.Controller;

import absensi.DAO.DAOUser;
import absensi.Model.User;

/**
 *
 * @author ASUS
 */
public class ControllerUser {

    private DAOUser daoUser;

    public ControllerUser() {
        daoUser = new DAOUser();
    }

    public User login(String username, String password) {
        return daoUser.login(username, password);
    }
}
