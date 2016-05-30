/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rapportprobleme.jdbc.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rapportprobleme.jdbc.model.beans.Probleme;
import rapportprobleme.jdbc.model.beans.Utilisateur;

/**
 *
 * @author Antho
 */
public class UtilisateurDAO extends DAO<Utilisateur> {

    @Override
    public Utilisateur find(int id_user) {
        Utilisateur u = null;

        try {
            String sql = "SELECT * FROM Utilisateur WHERE id_user=?";
            PreparedStatement pst = this.connexion.prepareStatement(sql);
            pst.setInt(1, id_user);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                u = new Utilisateur(
                        rs.getInt("id_user"),
                        rs.getString("login"),
                        rs.getString("password")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;

    }

    @Override
    public List<Utilisateur> findAll() {
        List<Utilisateur> utilisateurList = new LinkedList();
        try {
            String sql = "SELECT * FROM Utilisateurs";
            ResultSet rs = this.connexion.createStatement().executeQuery(sql);
            while (rs.next()) {
                utilisateurList.add(new Utilisateur(
                        rs.getInt("id_user"),
                        rs.getString("login"),
                        rs.getString("password")
                )
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return utilisateurList;
    }

    @Override
    public Utilisateur create(Utilisateur u) {
        this.setChanged();
        try {
            String sql = "INSERT INTO Utilisateur (login, password) VALUES (?,?) ";
            PreparedStatement pst = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, u.getLogin());
            pst.setString(2, u.getPassword());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            while (rs.next()) {
                u.setId_user(rs.getInt(1));
            }
        } catch (SQLException ex) {
            this.notifyObservers(ex);
            Logger.getLogger(UtilisateurDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.notifyObservers(u);
        return u;
    }

    @Override
    public Utilisateur update(Utilisateur u) {
        try {
            String sql = "UPDATE Utilisateur SET login=?, password=? WHERE id_user=?";
            PreparedStatement pst = this.connexion.prepareStatement(sql);
            pst.setString(1, u.getLogin());
            pst.setString(2, u.getPassword());
            pst.setInt(3, u.getId_user());
            pst.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(UtilisateurDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return u;
    }

    @Override
    public void delete(Utilisateur u) {
        this.setChanged();
        try {
            String sql = "DELETE FROM Utilisateur WHERE id_user=?";
            PreparedStatement pst = connexion.prepareStatement(sql);
            pst.setInt(1, u.getId_user());
            pst.executeUpdate();

        } catch (SQLException ex) {
            this.notifyObservers(ex);
            ex.printStackTrace();
        }
        this.notifyObservers();
    }

}
