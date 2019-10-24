/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sys;

import java.util.Date;

/**
 *
 * @author AllanRamiro
 */
public interface sysutil {
  public Date getFechaHoy(); 
  public java.sql.Timestamp getFechaHoraHoy();
  public java.sql.Timestamp getDateTime(java.util.Date  var_fecha);
  public int NroCorrelativo(String Tabla) throws Exception;
}
