/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;
import reportes.rptrecibo;

/**
 *
 * @author AllanRamiro
 */
@Named(value = "ImprimeReciboBean")
@RequestScoped
public class ImprimeReciboBean implements Serializable {

    /**
     * Creates a new instance of ImprimeReciboBean
     */
    
    private boolean rsp;
    private int nro_recibo = 0;
    public ImprimeReciboBean() {
 
         rsp = FacesContext.getCurrentInstance().isPostback();

        if (rsp == false) {
        this.nro_recibo = 73;
        }
    }

    public int getNro_recibo() {
        return nro_recibo;
    }

    public void setNro_recibo(int nro_recibo) {
        this.nro_recibo = nro_recibo;
    }

    
    
    //Declaracion del Metodo para invocar el reporte desde el xhtml
    public void rptRecibo() throws SQLException, ClassNotFoundException
            , InstantiationException, IllegalAccessException, ParseException {

        //Instancia la clase o el metodo rptRecibo, es la que se encargada de generar el un archivo pdf        
        rptrecibo rpt = new rptrecibo();

        // Se captura y se instancia el Contexto de la Vista levantada por  ViewScope
        FacesContext fc = FacesContext.getCurrentInstance();

        // Se instancia servlet para determinar la ruta real de la Aplicacion
        ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
        String path =  sc.getRealPath("rpt/rptrecibodepago.jasper");  

        rpt.getReporte(path, this.nro_recibo);

        // Cuando finaliza de procesar el Contexto del Servlet dentro de la vista, lo muestra en pantalla        
        FacesContext.getCurrentInstance().responseComplete();
    }

}
