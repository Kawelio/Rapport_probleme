/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rapportprobleme.jdbc.model.dao;

import java.util.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import rapportprobleme.jdbc.model.beans.Probleme;

/**
 *
 * @author Asus
 */
public class ProblemeDAO extends DAO<Probleme> {

    @Override
    public Probleme find(int id) {
        Probleme p = null;
        try {
            String sql = "SELECT * FROM Problemes WHERE id_pb=?";
            ResultSet rs = this.connexion.createStatement().executeQuery(sql);
            while (rs.next()) {
                p = new Probleme(
                        rs.getInt("id_pb"),
                        rs.getString("description"),
                        rs.getInt("priority"),
                        rs.getInt("state")
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProblemeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;

    }

    @Override
    public List<Probleme> findAll() {
        List<Probleme> problemesList = new LinkedList();
        try {
            String sql = "SELECT * FROM Problemes";
            ResultSet rs = this.connexion.createStatement().executeQuery(sql);
            while (rs.next()) {
                problemesList.add(new Probleme(
                        rs.getInt("id_pb"),
                        rs.getString("description"),
                        rs.getInt("priority"),
                        rs.getInt("state")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProblemeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return problemesList;

    }

    @Override
    public Probleme create(Probleme p) {
        this.setChanged();
        try {
            String sql = "INSERT INTO Problemes (description, priority, state) VALUES (?,?)";
            PreparedStatement pst = this.connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, p.getDescription());
            pst.setInt(2, p.getPriority());
            pst.setInt(3, p.getState());
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            while (rs.next()) {
                p.setId_pb(rs.getInt(1));
            }
        } catch (SQLException ex) {
            this.notifyObservers(ex);
            Logger.getLogger(ProblemeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.notifyObservers(p);
        return p;
    }

    @Override
    public Probleme update(Probleme p) {
        try {
            String sql = "UPDATE Problemes SET description=?, priority=?, state=? WHERE id_pb=?";
            PreparedStatement pst = this.connexion.prepareStatement(sql);
            pst.setString(1, p.getDescription());
            pst.setInt(2, p.getPriority());
            pst.setInt(3, p.getState());
            pst.setInt(4, p.getId_pb());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProblemeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    @Override
    public void delete(Probleme p) {
        this.setChanged();

        try {
            String sql = "DELETE FROM Problemes WHERE id_pb=?";
            PreparedStatement pst = this.connexion.prepareStatement(sql);
            pst.setInt(1, p.getId_pb());
            pst.executeUpdate();
        } catch (SQLException ex) {
            this.notifyObservers(ex);
            Logger.getLogger(ProblemeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.notifyObservers(p);

    }

}
