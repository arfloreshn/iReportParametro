/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import sys.sysutil;
import sys.sysutilImpl;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import static sys.dbConexcion.getConnection;

/**
 *
 * @author AllanRamiro
 */
public class rptfechas {
        
    
       public void getReporte(String ruta, Date fdesde, Date fhasta ) throws ClassNotFoundException
               , InstantiationException, IllegalAccessException, SQLException, ParseException {

        Connection conexion = null;
        //Declaramos un objecto que hereda todos los metodos y propiedades la sysutilImpl()
        sysutil objFecha = new sysutilImpl();
    
        try {
       //Conexcion a JODBC Mysql    
        conexion = getConnection();

        //Se definen los parametros si es que el reporte necesita
        Map parameter = new HashMap();
        // Cree una función para convertir los campos Date a TimeStamp
        // iReporte solo trabaja bien varias de Tipo TimeStamp para las fechas
        java.sql.Timestamp desde =  objFecha.getDateTime(fdesde);   //Convertimos lo datos de Tipo Date a TimeStamp
        java.sql.Timestamp hasta = objFecha.getDateTime(fhasta);    //Convertimos lo datos de Tipo Date a TimeStamp
        
        parameter.put("p_desde", desde); // Usamos el put para enviarle los datos al reporte, la variable "p_desde" debe llamarse exactamente con esta creada en el reporte
        parameter.put("p_hasta", hasta); // Usamos el put para enviarle los datos al reporte, la variable "p_hasta" debe llamarse exactamente con esta creada en el reporte

        File file = new File(ruta);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());
            //Llenado de la información
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conexion);

            JRPdfExporter pdfExporter = new JRPdfExporter();
            pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream();
     
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfReportStream));
            pdfExporter.exportReport();
           
            response.setContentType("application/pdf");
            response.setHeader("Content-Length", String.valueOf(pdfReportStream.size()));
            response.addHeader("Content-Disposition", "inline; filename=jasper.pdf;");
 
            
            OutputStream responseOutputStream = response.getOutputStream();
            responseOutputStream.write(pdfReportStream.toByteArray());
            responseOutputStream.close();
            pdfReportStream.close();
       
            
            if (pdfExporter != null) {
                try {
                  pdfExporter.exportReport();
                } catch (JRException e) {
                  //  logger.info(e.getMessage())
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
