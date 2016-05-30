/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rapportprobleme.jdbc.model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author acourjon
 */
public class ConnexionMySql {

    private String dbHost;
    private String dbName;
    private String user;
    private String pass;
    private final String driverPass = "com.mysql.jdbc.Driver";

    private static Connection instance;

    private ConnexionMySql() {

        readProperties();

        try {
            Class.forName(driverPass);
            this.instance = DriverManager.getConnection(
                    "jdbc:mysql://" + dbHost + "/" + dbName,
                    user,
                    pass
            );
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getInstance() {
        if (instance == null) {
            instance = new ConnexionMySql().instance;
        }
        return instance;
    }

    private void readProperties() {
        Properties properties = new Properties();
        try {
            FileInputStream in = new FileInputStream("conf.txt");
            properties.load(in);
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.dbHost = properties.getProperty("dbHost", "unknown");
        this.dbName = properties.getProperty("dbName", "unknown");
        this.pass = properties.getProperty("pass", "unknown");
        this.user = properties.getProperty("user", "unknown");
    }

    @Override
    public void finalize() {
        try {
            instance.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
