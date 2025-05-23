/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abc;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;

import javax.swing.JOptionPane;



public class Export {
    
    public static void Imprimir(String currStatement) throws FileNotFoundException, DocumentException, SQLException{
        Connection c = ConexionSQL.conectar();
        // HACER LA CONSULTA
        ConexionSQL.conectar();
        Statement st = c.createStatement();
        //Ahora si genera la última consulta con la cual se crea la tabla.
        ResultSet rs = st.executeQuery(currStatement);
        
        
        //Documento PDF
        //Nuevos cambios para que la página sea horizontal
        Document documento = new Document(PageSize.A4.rotate());
        
        //Guardar el documento en documentos jaja
        String nombre = JOptionPane.showInputDialog(null, "Introduce el nombre del archivo: ");
        
        String userHome = System.getProperty("user.home");
        String ruta = userHome + "/Documents/" + nombre + ".pdf";
        
        PdfWriter.getInstance(documento, new FileOutputStream(ruta));
        documento.open();
        
        
        ResultSetMetaData metaData = rs.getMetaData();
        int numColumnas = metaData.getColumnCount();
        
        
        //Crear la tabla
        PdfPTable tabla = new PdfPTable(numColumnas);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(10f);
        tabla.setSpacingAfter(10f);
        
        //ENCABEZADOS
        for(int i = 1; i <= numColumnas; i++) {
            String nombreCol = metaData.getColumnName(i);
            PdfPCell encabezado = new PdfPCell(new Phrase(nombreCol));
            encabezado.setBackgroundColor(new Color(200, 150, 200));
            encabezado.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            encabezado.setPadding(5);
            tabla.addCell(encabezado); 
        }
        
        while(rs.next()) {
            for(int i = 1; i <= numColumnas; i++) {
                Object valor = rs.getObject(i);
                PdfPCell celdaDato = new PdfPCell(new Phrase(valor != null ? valor.toString() : ""));
                celdaDato.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                celdaDato.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
                celdaDato.setPadding(2);
                tabla.addCell(celdaDato);
            }
        }
        
        documento.add(tabla);
        documento.close();
        
        JOptionPane.showMessageDialog(null, "Documento generado en " + ruta);
    }   
}
