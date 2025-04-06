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
        
        
        String preparedString = "INSERT INTO " + entidad + "(" + this.crearTable.getColumnName(1);
        for(int i = 2; i < this.crearTable.getColumnCount(); i++){
            preparedString += " , " + this.crearTable.getColumnName(i);
        }
        preparedString += ")";
        
        
                
        preparedString += " values ('" + this.crearTable.getValueAt(0,1) + "' ";
        for (int i = 2; i < this.crearTable.getColumnCount(); i++) {
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
