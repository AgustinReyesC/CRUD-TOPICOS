/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package abc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author AJMM
 */
public class crearFrame extends javax.swing.JFrame {

    String entidad;
    
    public crearFrame(DefaultTableModel crearTableModel, String entidad) {
        initComponents();
        this.crearTable.setModel(crearTableModel);
        this.crearTable.setRowHeight(20);
        
        this.entidad = entidad;   
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        crearTable = new javax.swing.JTable();
        crearBoton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        crearTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        crearTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(crearTable);

        crearBoton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        crearBoton.setText("CREAR");
        crearBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearBotonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(283, 283, 283)
                        .addComponent(crearBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(crearBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void crearBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearBotonActionPerformed
        
        Connection c = ConexionSQL.conectar();
        
        //validamos la entrada
        int renglon = 0;
        //validar genero
            if (crearTable.getColumnName(0).toUpperCase().equals("GENERO") || crearTable.getColumnName(0).toUpperCase().equals("GÉNERO")){
                String generos[] = {"MASCULINO", "FEMENINO", "OTRO", "NO BINARIO","PREFIERO NO DECIR"};
                boolean validado = false;
                
                for (var g:generos){
                    if(g.equals(crearTable.getValueAt(renglon, 0).toString().toUpperCase())){
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
            if(crearTable.getColumnName(0).toUpperCase().equals("TELEFONO") || crearTable.getColumnName(0).toUpperCase().equals("TELÉFONO")){
                String numeroDeTelefono = crearTable.getValueAt(renglon, 0).toString();
                boolean telefonoValido = numeroDeTelefono.matches("\\d{8}") || numeroDeTelefono.matches("\\d{4}-\\d{4}") || numeroDeTelefono.matches("\\d{3}-\\d{3}-\\d{2}") || numeroDeTelefono.matches("\\d{3}-\\d{3}-\\d{4}") || numeroDeTelefono.matches("\\d{10}");
         
                if (!telefonoValido){
                    JOptionPane.showMessageDialog(null, "VERIFIQUE EL NUMERO DE TELÉFONO, DEBE ESTAR EN UNO DE LOS SIGUIENTES FORMATOS: 12345678, 1234-5678, 123-456-78, 1234567890, 123-456-7890");
                    return;
                }
                
            }
            
            //validar promedio
            if(crearTable.getColumnName(0).toUpperCase().equals("PROMEDIO")){
                String promedio = crearTable.getValueAt(renglon, 0).toString();
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
            if (crearTable.getColumnName(0).toUpperCase().contains("EMAIL") || crearTable.getColumnName(0).toUpperCase().contains("CORREO")) {
                String correoElectronico = crearTable.getValueAt(renglon, 0).toString();

                // Expresión regular para validar un correo electrónico
                boolean correoValido = correoElectronico.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

                if (!correoValido) {
                    JOptionPane.showMessageDialog(null, "VERIFIQUE EL CORREO ELECTRÓNICO, DEBE ESTAR EN UN FORMATO VÁLIDO: ejemplo@dominio.com");
                    return;
                }
            }
            
        String preparedString = "INSERT INTO " + entidad + "(" + this.crearTable.getColumnName(0);
        for(int i = 1; i < this.crearTable.getColumnCount(); i++){
            
            //validar genero
            if (crearTable.getColumnName(i).toUpperCase().equals("GENERO") || crearTable.getColumnName(i).toUpperCase().equals("GÉNERO")){
                String generos[] = {"MASCULINO", "FEMENINO", "OTRO", "NO BINARIO","PREFIERO NO DECIR"};
                boolean validado = false;
                
                for (var g:generos){
                    if(g.equals(crearTable.getValueAt(renglon, i).toString().toUpperCase())){
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
            if(crearTable.getColumnName(i).toUpperCase().equals("TELEFONO") || crearTable.getColumnName(i).toUpperCase().equals("TELÉFONO")){
                String numeroDeTelefono = crearTable.getValueAt(renglon, i).toString();
                boolean telefonoValido = numeroDeTelefono.matches("\\d{8}") || numeroDeTelefono.matches("\\d{4}-\\d{4}") || numeroDeTelefono.matches("\\d{3}-\\d{3}-\\d{2}") || numeroDeTelefono.matches("\\d{3}-\\d{3}-\\d{4}") || numeroDeTelefono.matches("\\d{10}");
         
                if (!telefonoValido){
                    JOptionPane.showMessageDialog(null, "VERIFIQUE EL NUMERO DE TELÉFONO, DEBE ESTAR EN UNO DE LOS SIGUIENTES FORMATOS: 12345678, 1234-5678, 123-456-78, 1234567890, 123-456-7890");
                    return;
                }
                
            }
            
            //validar promedio
            if(crearTable.getColumnName(i).toUpperCase().equals("PROMEDIO")){
                String promedio = crearTable.getValueAt(renglon, i).toString();
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
            if (crearTable.getColumnName(i).toUpperCase().contains("EMAIL") || crearTable.getColumnName(i).toUpperCase().contains("CORREO")) {
                String correoElectronico = crearTable.getValueAt(renglon, i).toString();

                // Expresión regular para validar un correo electrónico
                boolean correoValido = correoElectronico.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

                if (!correoValido) {
                    JOptionPane.showMessageDialog(null, "VERIFIQUE EL CORREO ELECTRÓNICO, DEBE ESTAR EN UN FORMATO VÁLIDO: ejemplo@dominio.com");
                    return;
                }
            }
            
            preparedString += " , " + this.crearTable.getColumnName(i);
        }
        preparedString += ")";
        
        
                
        preparedString += " values ('" + this.crearTable.getValueAt(0,1) + "' ";
        for (int i = 1; i < this.crearTable.getColumnCount(); i++) {
            preparedString += ", '" + this.crearTable.getValueAt(0, i) + "'";
        }
        preparedString += " )";

        
        
        try {
            PreparedStatement pSt = c.prepareStatement(preparedString);
            pSt.executeUpdate();
            
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "ERROR " + ex.toString());
        }finally{
            ConexionSQL.desconectar(c);
        } 
    }//GEN-LAST:event_crearBotonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton crearBoton;
    private javax.swing.JTable crearTable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
