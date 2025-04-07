/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abc;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author gutyc
 */

//checando branches
//NUEVA CLASE PARA ALMACENAR SÓLO LAS CONSULTAS SQL (MEJOR ORGANIZACIÓN)

public class ConexionSQL {
    
    public static Connection conectar(){
        Connection c = null;
               
        try {
            Class.forName("com.mysql.jdbc.Driver");//cargar el driver d mysql a memoria, esto visto en: https://stackoverflow.com/questions/5556664/how-to-fix-no-suitable-driver-found-for-jdbcmysql-localhost-dbname-error-w
            String cadena = "jdbc:mysql://localhost:3306/escuela?useUnicode=true&characterEncoding=utf8";
            
            c =  DriverManager.getConnection(cadena,"root","");  
            c.setAutoCommit(true);
            //JOptionPane.showMessageDialog(null,"¡Conexion exitosa!");
            
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null,"No se pudo conectar a la base de datos.." + ex.toString());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
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
            
            //validamos la entrada, debemos validar el primero por separado porque se añade por separado
            //validar genero
            if (registrosTable.getColumnName(1).toUpperCase().equals("GENERO") || registrosTable.getColumnName(1).toUpperCase().equals("GÉNERO")){
                String generos[] = {"MASCULINO", "FEMENINO", "OTRO", "NO BINARIO","PREFIERO NO DECIR"};
                boolean validado = false;
                
                for (var g:generos){
                    if(g.equals(registrosTable.getValueAt(renglon, 1).toString().toUpperCase())){
                        validado = true;
                        break;
                    }
                }
                
                if (!validado){
                    JOptionPane.showMessageDialog(null, "VERIFIQUE EL VALOR DE GÉNERO (DEBE SER UNO DE LOS SIGUIENTES): MASCULINO, FEMENINO, NO BINARIO, OTRO, PREFIERO NO DECIR");
                    return;
                }
            }
            
            //validar telefono
            if(registrosTable.getColumnName(1).toUpperCase().equals("TELEFONO") || registrosTable.getColumnName(1).toUpperCase().equals("TELÉFONO")){
                String numeroDeTelefono = registrosTable.getValueAt(renglon, 1).toString();
                boolean telefonoValido = numeroDeTelefono.matches("\\d{8}") || numeroDeTelefono.matches("\\d{4}-\\d{4}") || numeroDeTelefono.matches("\\d{3}-\\d{3}-\\d{2}") || numeroDeTelefono.matches("\\d{3}-\\d{3}-\\d{4}") || numeroDeTelefono.matches("\\d{10}");
         
                if (!telefonoValido){
                    JOptionPane.showMessageDialog(null, "VERIFIQUE EL NUMERO DE TELÉFONO, DEBE ESTAR EN UNO DE LOS SIGUIENTES FORMATOS: 12345678, 1234-5678, 123-456-78, 1234567890, 123-456-7890");
                    return;
                }
                
            }
            
            //validar promedio
            if(registrosTable.getColumnName(1).toUpperCase().equals("PROMEDIO")){
                String promedio = registrosTable.getValueAt(renglon, 1).toString();
                boolean promedioValido = false;
                try{
                    double promedioD = Double.parseDouble(promedio);
                    promedioValido = promedioD <= 10 && promedioD >= 0;
                
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "VERIFIQUE EL PROMEDIO, DEBE SER UN VALOR NUMÉRICO DEL 1 al 10");
                    return;
                }
                if (!promedioValido){
                    JOptionPane.showMessageDialog(null, "VERIFIQUE EL PROMEDIO, DEBE SER UN VALOR NUMÉRICO DEL 1 al 10");
                    return;
                }
                
            }
            
            //validar correo electrónico
            if (registrosTable.getColumnName(1).toUpperCase().contains("EMAIL") || registrosTable.getColumnName(1).toUpperCase().contains("CORREO")) {
                String correoElectronico = registrosTable.getValueAt(renglon, 1).toString();

                // Expresión regular para validar un correo electrónico
                boolean correoValido = correoElectronico.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

                if (!correoValido) {
                    JOptionPane.showMessageDialog(null, "VERIFIQUE EL CORREO ELECTRÓNICO, DEBE ESTAR EN UN FORMATO VÁLIDO: ejemplo@dominio.com");
                    return;
                }
            }
        
        String preparedString = "UPDATE " + entidad + " SET " + registrosTable.getColumnName(1) + " = '" +  registrosTable.getValueAt(renglon, 1)  + "'";
        for(int i = 2; i < registrosTable.getColumnCount(); i++){
            //validar genero
            if (registrosTable.getColumnName(i).toUpperCase().equals("GENERO") || registrosTable.getColumnName(i).toUpperCase().equals("GÉNERO")){
                String generos[] = {"MASCULINO", "FEMENINO", "OTRO", "NO BINARIO","PREFIERO NO DECIR"};
                boolean validado = false;
                
                for (var g:generos){
                    if(g.equals(registrosTable.getValueAt(renglon, i).toString().toUpperCase())){
                        validado = true;
                        break;
                    }
                }
                
                if (!validado){
                    JOptionPane.showMessageDialog(null, "VERIFIQUE EL VALOR DE GÉNERO (DEBE SER UNO DE LOS SIGUIENTES): MASCULINO, FEMENINO, NO BINARIO, OTRO, PREFIERO NO DECIR");
                    return;
                }
            }
            
            //validar telefono
            if(registrosTable.getColumnName(i).toUpperCase().equals("TELEFONO") || registrosTable.getColumnName(i).toUpperCase().equals("TELÉFONO")){
                String numeroDeTelefono = registrosTable.getValueAt(renglon, i).toString();
                boolean telefonoValido = numeroDeTelefono.matches("\\d{8}") || numeroDeTelefono.matches("\\d{4}-\\d{4}") || numeroDeTelefono.matches("\\d{3}-\\d{3}-\\d{2}") || numeroDeTelefono.matches("\\d{3}-\\d{3}-\\d{4}") || numeroDeTelefono.matches("\\d{10}");
         
                if (!telefonoValido){
                    JOptionPane.showMessageDialog(null, "VERIFIQUE EL NUMERO DE TELÉFONO, DEBE ESTAR EN UNO DE LOS SIGUIENTES FORMATOS: 12345678, 1234-5678, 123-456-78, 1234567890, 123-456-7890");
                    return;
                }
                
            }
            
            //validar promedio
            if(registrosTable.getColumnName(i).toUpperCase().equals("PROMEDIO")){
                String promedio = registrosTable.getValueAt(renglon, i).toString();
                boolean promedioValido = false;
                try{
                    double promedioD = Double.parseDouble(promedio);
                    promedioValido = promedioD <= 10 && promedioD >= 0;
                
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "VERIFIQUE EL PROMEDIO, DEBE SER UN VALOR NUMÉRICO DEL 1 al 10");
                    return;
                }
                if (!promedioValido){
                    JOptionPane.showMessageDialog(null, "VERIFIQUE EL PROMEDIO, DEBE SER UN VALOR NUMÉRICO DEL 1 al 10");
                    return;
                }
                
            }
            
            //validar correo electrónico
            if (registrosTable.getColumnName(i).toUpperCase().contains("EMAIL") || registrosTable.getColumnName(i).toUpperCase().contains("CORREO")) {
                String correoElectronico = registrosTable.getValueAt(renglon, i).toString();

                // Expresión regular para validar un correo electrónico
                boolean correoValido = correoElectronico.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

                if (!correoValido) {
                    JOptionPane.showMessageDialog(null, "VERIFIQUE EL CORREO ELECTRÓNICO, DEBE ESTAR EN UN FORMATO VÁLIDO: ejemplo@dominio.com");
                    return;
                }
            }

            
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
}
