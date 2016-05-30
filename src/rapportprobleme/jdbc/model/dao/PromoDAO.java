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
import rapportprobleme.jdbc.model.beans.Promo;

/**
 *
 * @author Antho
 */
public class PromoDAO extends DAO<Promo> {

    @Override
    public Promo find(int id) {
        Promo p = null;
        try {
            String sql = "SELECT * FROM Promo WHERE id_cr=?";
            PreparedStatement pst = this.connexion.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                p = new Promo(
                        rs.getInt("id_cr"),
                        rs.getString("title")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(PromoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;

    }

    @Override
    public List<Promo> findAll() {
        List<Promo> promoList = new LinkedList();

        try {
            String sql = "SELECT * FROM Promo";
            ResultSet rs = this.connexion.createStatement().executeQuery(sql);
            while (rs.next()) {
                promoList.add(new Promo(
                        rs.getInt("id_cr"),
                        rs.getString("title")
                ));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return promoList;
    }

    @Override
    public Promo create(Promo p) {
        this.setChanged();
        try {
            String sql = "INSERT INTO Promo (title) VALUES (?)";
            PreparedStatement pst = this.connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, p.getTitle());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            while (rs.next()) {
                p.setId_cr(rs.getInt(1));
            }
        } catch (SQLException ex) {
            this.notifyObservers(ex);
            Logger.getLogger(PromoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    @Override
    public Promo update(Promo p) {
        this.setChanged();
        try {
            String sql = "UPDATE Promo SET title=? WHERE id_cr=?";
            PreparedStatement pst = this.connexion.prepareStatement(sql);
            pst.setString(1, p.getTitle());
            pst.setInt(2, p.getId_cr());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PromoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;

    }

    @Override
    public void delete(Promo p) {
        this.setChanged();

        try {
            String sql = "DELETE FROM Promo WHERE id_cr=?";
            PreparedStatement pst = this.connexion.prepareStatement(sql);
            pst.setInt(1, p.getId_cr());
            pst.executeUpdate();
        } catch (SQLException ex) {
            this.notifyObservers(ex);
            ex.printStackTrace();
        }
        this.notifyObservers(p);

    }

}
