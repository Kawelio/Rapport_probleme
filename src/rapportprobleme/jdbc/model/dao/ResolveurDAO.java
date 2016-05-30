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
import rapportprobleme.jdbc.model.beans.Resolveur;

/**
 *
 * @author Antho
 */
public class ResolveurDAO extends DAO<Resolveur> {

    @Override
    public Resolveur find(int id) {
        Resolveur r = null;
        try {
            String sql = "SELECT * FROM Resolveur WHERE id_rsv";
            PreparedStatement pst = this.connexion.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                r = new Resolveur(
                        rs.getInt("id_rsv"),
                        rs.getString("name"),
                        rs.getString("first"),
                        rs.getInt("phone"),
                        rs.getString("mail")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(ResolveurDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    @Override
    public List<Resolveur> findAll() {
        List<Resolveur> resolveurList = new LinkedList();
        try {
            String sql = "SELECT * FROM Resolveurs";
            ResultSet rs = this.connexion.createStatement().executeQuery(sql);

            while (rs.next()) {
                resolveurList.add(new Resolveur(
                        rs.getInt("id_rsv"),
                        rs.getString("name"),
                        rs.getString("first"),
                        rs.getInt("phone"),
                        rs.getString("mail")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resolveurList;
    }

    @Override
    public Resolveur create(Resolveur r) {
        this.setChanged();
        try {
            String sql = "INSERT INTO Resolveurs (name, first, phone, mail) VALUES (?,?,?,?)";
            PreparedStatement pst = this.connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, r.getName());
            pst.setString(2, r.getFirst());
            pst.setInt(3, r.getPhone());
            pst.setString(4, r.getMail());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            while (rs.next()) {
                r.setId_rsv(rs.getInt(1));
            }
        } catch (SQLException ex) {
            this.notifyObservers(ex);
            Logger.getLogger(ResolveurDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.notifyObservers(r);
        return r;
    }

    @Override
    public Resolveur update(Resolveur r) {
        try {
            String sql = "UPDATE Resolveur SET name=?, first=?, phone=?, mail=? WHERE id_rsv=?";
            PreparedStatement pst = this.connexion.prepareStatement(sql);
            pst.setString(1, r.getName());
            pst.setString(2, r.getFirst());
            pst.setInt(3, r.getPhone());
            pst.setString(4, r.getMail());
            pst.setInt(5, r.getId_rsv());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ResolveurDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    @Override
    public void delete(Resolveur r) {
        this.setChanged();
        try {
            String sql = "DELETE FROM Resolveur WHERE id_rsv=?";
            PreparedStatement pst = this.connexion.prepareStatement(sql);
            pst.setInt(1, r.getId_rsv());
            pst.executeUpdate();
        } catch (SQLException ex) {
            this.notifyObservers();
            ex.printStackTrace();
        }
        this.notifyObservers();
    }

}
