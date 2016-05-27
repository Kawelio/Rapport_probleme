/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor
 */
package rapportprobleme.jdbc.model.beans;

/**
 *
 * @author Asus
 */
public class Utilisateur {
    private int id_user;
    private String login;
    private String password;

    public Utilisateur(int id_user, String login, String password) {
        this.id_user = id_user;
        this.login = login;
        this.password = password;
    }
    
}
