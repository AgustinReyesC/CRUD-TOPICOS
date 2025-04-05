/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gutyc
 */


//NUEVA CLASE PARA ALMACENAR SÓLO LAS CONSULTAS SQL (MEJOR ORGANIZACIÓN)


public class ConexionSQL {
    
    
    public static Connection conectar(){
               Connection c = null;
               
        try {
            String cadena = "jdbc:sqlserver://localhost:1433;databaseName=BIBLIOTECA;integratedSecurity=false;encrypt=true;trustServerCertificate=true";
      
            c =  DriverManager.getConnection(cadena,"sa","sotaaglesotaagle1");  
            c.setAutoCommit(true);
            //JOptionPane.showMessageDialog(null,"¡Conexion exitosa!");
            
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null,"No se pudo conectar a la base de datos.." + ex.toString());
        }
       
        return c;
    }
    
    
    public static void desconectar(Connection c) {
        try {
            c.close();
            //JOptionPane.showMessageDialog(null, "¡Desconexion exitosa!");
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "Error de cierre de la BD!!! " + ex.getMessage());
        }
    }
    
    
    public static void eliminar(String entidad, int id, Map<String, String> dic){
        Connection c = conectar();

        String cad = "DELETE FROM " + entidad + " WHERE "+ dic.get(entidad) +" =" + id;
        PreparedStatement oPS;
        try {
            oPS = c.prepareStatement(cad);
            oPS.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ERROR DE SQL " + ex.toString());
        } finally {
            desconectar(c);
        }
    } 
    
    
    
    public static void modificarUnRegistro(String entidad, int renglon, JTable registrosTable, Map<String, String> dic){
        Connection c = conectar();             
        String preparedString = "UPDATE " + entidad + " SET " + registrosTable.getColumnName(1) + " = '" +  registrosTable.getValueAt(renglon, 1)  + "'";
        for(int i = 2; i < registrosTable.getColumnCount(); i++){
            preparedString += ", " + registrosTable.getColumnName(i) + " = '" +  registrosTable.getValueAt(renglon, i) + "'";
        }

        preparedString += "WHERE " + dic.get(entidad) + " = " + registrosTable.getValueAt(renglon, 0);
        
        PreparedStatement oPS;
        try {
            oPS = c.prepareStatement(preparedString);
            oPS.executeUpdate();
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "ERROR DE SQL " + ex.toString());

        } finally {
            desconectar(c);
        }
    }
    
    
    /*
    //nuevas
    public static DefaultTableModel busquedasSQL(String entidad, String atributo, String valor) {
        Connection c = conectar();
        String preparedString = "SELECT * FROM " + entidad + " WHERE " + atributo + " = " + valor;
        
        PreparedStatement oPS;
        
        try {
            
        }
    }*/
    
    
    
    
    
    
    
    
}
