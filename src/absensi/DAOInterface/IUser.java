/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package absensi.DAOInterface;

import absensi.Model.User;
/**
 *
 * @author ASUS
 */
public interface IUser {
    User login(String username, String password);
}
