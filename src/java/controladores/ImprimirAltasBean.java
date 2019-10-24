/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import reportes.rptfechas;

/**
 *
 * @author AllanRamiro
 */
@Named(value = "ImprimirAltas")
@RequestScoped
public class ImprimirAltasBean implements Serializable{

    /**
     * Creates a new instance of pametrosdeFechaBean
     */
    
    private Date fdesde;
    private Date fhasta;
   private boolean rsp;
  
    public ImprimirAltasBean() {
          // long d = System.currentTimeMillis();
        rsp = FacesContext.getCurrentInstance().isPostback();

        if (rsp == false) {
            this.fdesde = new Date();
            this.fhasta = new Date();
        }
    }

    public Date getFdesde() {
        return fdesde;
    }
    
    
      public void setFdesde(Date fdesde) {
        this.fdesde = fdesde;
    }

    public Date getFhasta() {
        return fhasta;
    }

    public void setFhasta(Date fhasta) {
        this.fhasta = fhasta;
    }

  
    //Declaracion del Metodo para invocar el reporte desde el xhtml
    public void rptImprimirAltas() throws SQLException, ClassNotFoundException
            , InstantiationException, IllegalAccessException, ParseException {

        //Instancia la clase o el metodo rptAltas, que es la encargada de generar el un archivo pdf        
        rptfechas rpt = new rptfechas();

        // Se captura y se instancia el Contexto de la Vista levantada por  ViewScope
        FacesContext fc = FacesContext.getCurrentInstance();

        // Se instancia servlet para determinar la ruta real de la Aplicacion
        ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
        String path =  sc.getRealPath("rpt/rptaltas.jasper"); 

        rpt.getReporte(path, this.fdesde, this.fhasta);

        // Cuando finaliza de procesar el Contexto del Servlet dentro de la vista, lo muestra en pantalla        
        fc.getCurrentInstance().responseComplete();
    }

}
