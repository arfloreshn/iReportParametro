/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sys;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AllanRamiro
 */
public class dbConexcion {

    public dbConexcion() {
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://192.168.0.19:3306/planilla", "root", "Denver$8");
        } catch (SQLException ex) {
            Logger.getLogger(dbConexcion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(dbConexcion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(dbConexcion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
