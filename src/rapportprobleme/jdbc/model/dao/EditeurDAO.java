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
import rapportprobleme.jdbc.model.beans.Editeur;

/**
 *
 * @author Antho
 */
public class EditeurDAO extends DAO<Editeur> {

    @Override
    public Editeur find(int id) {
        Editeur e = null;

        try {
            String sql = "SELECT * FROM Editeur WHERE id_edit=?";
            PreparedStatement pst = this.connexion.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                e = new Editeur(
                        rs.getInt("id_edit"),
                        rs.getString("name"),
                        rs.getString("first"),
                        rs.getInt("phone"),
                        rs.getString("mail")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditeurDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;

    }

    @Override
    public List<Editeur> findAll() {
        List<Editeur> editeurList = new LinkedList();

        try {
            String sql = "SELECT * FROM Editeur";
            ResultSet rs = this.connexion.createStatement().executeQuery(sql);
            while (rs.next()) {
                editeurList.add(new Editeur(
                        rs.getInt("id_edit"),
                        rs.getString("name"),
                        rs.getString("first"),
                        rs.getInt("phone"),
                        rs.getString("mail")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return editeurList;

    }

    @Override
    public Editeur create(Editeur e) {
        this.setChanged();
        try {
            String sql = "INSERT INTO Editeurs (name, first, phone, mail) VALUES (?,?,?,?)";
            PreparedStatement pst = this.connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, e.getName());
            pst.setString(2, e.getFirst());
            pst.setInt(3, e.getPhone());
            pst.setString(4, e.getMail());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            while (rs.next()) {
                e.setId_edit(rs.getInt(1));
            }
        } catch (SQLException ex) {
            this.notifyObservers(ex);
            Logger.getLogger(ResolveurDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.notifyObservers(e);
        return e;
    }

    @Override
    public Editeur update(Editeur e) {
        try {
            String sql = "UPDATE Editeurs SET name=?, first=?, phone=?, mail=? WHERE id_edit=?";
            PreparedStatement pst = this.connexion.prepareStatement(sql);
            pst.setString(1, e.getName());
            pst.setString(2, e.getFirst());
            pst.setInt(3, e.getPhone());
            pst.setString(4, e.getMail());
            pst.setInt(5, e.getId_edit());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ResolveurDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return e;
    }

    @Override
    public void delete(Editeur e) {
        this.setChanged();
        try {
            String sql = "DELETE FROM Editeur WHERE id_edit=?";
            PreparedStatement pst = this.connexion.prepareStatement(sql);
            pst.setInt(1, e.getId_edit());
            pst.executeUpdate();
        } catch (SQLException ex) {
            this.notifyObservers();
            ex.printStackTrace();
        }
        this.notifyObservers();
    }

}
