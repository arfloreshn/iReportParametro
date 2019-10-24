/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sys;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author AllanRamiro
 */
public class sysutilImpl implements sysutil {
    
    
     private String sql = "";
     private ResultSet rs = null;
     private Statement st;
     private Connection con = null;
     public Date fechaSistema = null;
     private int var_nro_correlativo = 0;
   
     
    // Obtiene la fecha del sistema desde la base de datos 
    @Override
    public Date getFechaHoy() {
    try {
            this.con = dbConexcion.getConnection();
             st = con.createStatement();
             sql = "SELECT CURDATE()"; 
             this.rs = st.executeQuery(sql);
              while (rs.next()) {
               fechaSistema  =  rs.getDate(1);
             }
            con.close();
         } catch (SQLException ex) {
            ex.getMessage();
        } 
      return fechaSistema;
	
    }

    
    //Convierte la fecha sistema a TimeStamp
    @Override
    public Timestamp getFechaHoraHoy() {
        long ms =  getFechaHoy().getTime();
        java.sql.Timestamp ts = new java.sql.Timestamp(ms);
        return ts;
    }

    
    //Covierte los campos de tipo Date a TimeStamp, esto es necesario para los reporte de iReport
    @Override
    public Timestamp getDateTime(Date var_fecha) {
        java.sql.Timestamp ts = new java.sql.Timestamp(var_fecha.getTime());
        return ts;
    }
    
    
    
    //Obtiene un numero correlativo apartir de una funcion en la base de datos que recibe como parametro el nombre de la tabla
    public int NroCorrelativo(String Tabla) throws Exception {

        String sSql = "";

        sSql = "SELECT fn_correlativo('" + Tabla + "')";
        CallableStatement cs = dbConexcion.getConnection().prepareCall(sSql);
        ResultSet rs = cs.executeQuery();
        while (rs.next()) {
            this.var_nro_correlativo = rs.getInt(1); // return value
        }
        cs.close();

        return this.var_nro_correlativo;
    }

    
   
}
