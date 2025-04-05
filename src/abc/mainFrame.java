/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package abc;

import com.lowagie.text.DocumentException;
import java.awt.event.ItemEvent;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableModel;

/**

/**
 *
 * @author AJMM
 */
public class mainFrame extends javax.swing.JFrame {

    Connection c = null;  
    int paginaDeRegistro = 1;
    ArrayList<Integer> renglonesClickeados;
    Map<String, String> dic;

    
    public mainFrame() {
        initComponents();
        renglonesClickeados = new ArrayList();
        //desactivo los botones de modificar
        this.eliminarBoton.setEnabled(false);
        this.aplicarCambiosBoton.setEnabled(false);
        mostrarDesdeNRegistros(this.entidadComBox.getSelectedItem() + "", 0, paginaDeRegistro * 24, false, "", "");
        
        //Crear el mapa para encontrar IDs
        dic = new HashMap<>();
        dic.put("ESTUDIANTES", "id_estudiante");
        dic.put("PRESTAMO", "id_prestamo");
        
    }

    
    //Le voy a agregar un apartado para ver si puedo hacer búsquedas
    
    public void mostrarDesdeNRegistros(String entidad, int inicio, int fin, boolean condition, String atributo, String value){
        c = ConexionSQL.conectar();
        //-- Make sure to have an ORDER BY clause (even if it’s just a dummy one) OFFSET n - 1 ROWS FETCH NEXT m - n + 1 ROWS ONLY;
        Statement oSt;
        int cuantos = fin - inicio;
        int offset = inicio;
        try {
            //consigo los datos
            oSt = (Statement) c.createStatement();
            
            
            //apartado para buscar con condiciones
            String creandoStatement = "SELECT * FROM " + entidad;
            if (condition) {
                // Detectar si es numérico
                boolean esNumero = value.matches("-?\\d+(\\.\\d+)?");
                String filtro = esNumero 
                    ? atributo + " = " + value 
                    : atributo + " = '" + value + "'";
                creandoStatement += " WHERE " + filtro;
            }
            creandoStatement += " ORDER BY (SELECT NULL) " + "OFFSET " + offset + " ROWS FETCH NEXT " + cuantos + "ROWS ONLY";
            
            
            
            
            ResultSet rs = oSt.executeQuery(creandoStatement);
           
            

            //ajusto la tabla
            DefaultTableModel model = new DefaultTableModel();
            ResultSetMetaData metaData = rs.getMetaData();
           
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                model.addColumn(columnName);
            }
            
            //pongo todos los registros
            while (rs.next())  {
                Object currRegistro[] = new Object[metaData.getColumnCount()];
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    currRegistro[i - 1] = rs.getObject(i);
                }
                
                model.addRow(currRegistro);
            }
        //los hago no editables
        this.registrosTable.setModel(model);
        this.registrosTable.setEnabled(false);
        this.modificarCheckBox.setSelected(false);
            
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "ERROR DE SQL " + ex.toString());
        } finally {
            ConexionSQL.desconectar(c);
        }
        
    }
    
    public void llenarTabla(){
         Map<String, String> dicBusqueda = new HashMap<>();
        
        dicBusqueda.put("ALUMNO", "nombre1");

        
        
        String entidad = entidadComBox.getSelectedItem().toString();
        String valorBuscado = jTextField1.getText();
        String atributo = dicBusqueda.get(entidadComBox1.getSelectedItem().toString());

        if (atributo != null && !valorBuscado.isEmpty()) {
            mostrarDesdeNRegistros(entidad, (paginaDeRegistro - 1) * 24, paginaDeRegistro * 24, true, atributo, valorBuscado);
        } else {
            // O mostrás todo, o mostrás un mensaje de advertencia
            mostrarDesdeNRegistros(entidad, (paginaDeRegistro - 1) * 24, paginaDeRegistro * 24, false, "", "");
        }
    }
    

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        entidadComBox = new javax.swing.JComboBox<>();
        nuevoBoton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        registrosTable = new javax.swing.JTable();
        anterioresRegistrosBoton = new javax.swing.JButton();
        siguientesRegistrosBoton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        aplicarCambiosBoton = new javax.swing.JButton();
        modificarCheckBox = new javax.swing.JCheckBox();
        eliminarBoton = new javax.swing.JButton();
        eliminarBoton1 = new javax.swing.JButton();
        entidadComBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        entidadComBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        entidadComBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ESTUDIANTES", "PRESTAMO" }));
        entidadComBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                entidadComBoxItemStateChanged(evt);
            }
        });

        nuevoBoton.setText("NUEVO");
        nuevoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoBotonActionPerformed(evt);
            }
        });

        registrosTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        registrosTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registrosTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(registrosTable);

        anterioresRegistrosBoton.setText("<");
        anterioresRegistrosBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anterioresRegistrosBotonActionPerformed(evt);
            }
        });

        siguientesRegistrosBoton.setText(">");
        siguientesRegistrosBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siguientesRegistrosBotonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setToolTipText("Modificar");

        aplicarCambiosBoton.setText("APLICAR CAMBIOS");
        aplicarCambiosBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aplicarCambiosBotonActionPerformed(evt);
            }
        });

        modificarCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        modificarCheckBox.setText("Modificar");
        modificarCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                modificarCheckBoxItemStateChanged(evt);
            }
        });
        modificarCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                modificarCheckBoxStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(modificarCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(aplicarCambiosBoton, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(modificarCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(aplicarCambiosBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        eliminarBoton.setText("ELIMINAR SELECCIONADO");
        eliminarBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarBotonActionPerformed(evt);
            }
        });

        eliminarBoton1.setText("EXPORTAR PDF");
        eliminarBoton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarBoton1ActionPerformed(evt);
            }
        });

        entidadComBox1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        entidadComBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ALUMNO", "PROFESOR", "PROMEDIO" }));
        entidadComBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                entidadComBox1ItemStateChanged(evt);
            }
        });
        entidadComBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entidadComBox1ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(eliminarBoton1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addComponent(eliminarBoton)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(entidadComBox1, 0, 135, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(nuevoBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(entidadComBox, 0, 135, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(anterioresRegistrosBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(siguientesRegistrosBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(anterioresRegistrosBoton)
                    .addComponent(siguientesRegistrosBoton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(entidadComBox, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nuevoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(entidadComBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(eliminarBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(eliminarBoton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void anterioresRegistrosBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anterioresRegistrosBotonActionPerformed
        paginaDeRegistro = Math.max(paginaDeRegistro - 1, 1);
        llenarTabla(); 
    }//GEN-LAST:event_anterioresRegistrosBotonActionPerformed

    private void siguientesRegistrosBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siguientesRegistrosBotonActionPerformed
        paginaDeRegistro++;
        llenarTabla();
    }//GEN-LAST:event_siguientesRegistrosBotonActionPerformed

    private void entidadComBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_entidadComBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED){
           llenarTabla();
        }
    }//GEN-LAST:event_entidadComBoxItemStateChanged

    private void modificarCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_modificarCheckBoxStateChanged
      
    }//GEN-LAST:event_modificarCheckBoxStateChanged

    private void modificarCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_modificarCheckBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED){
            this.registrosTable.setEnabled(true);
            //activo los botones para modificar
            this.eliminarBoton.setEnabled(true);
            this.aplicarCambiosBoton.setEnabled(true);
            //desactivo los botones de cambiar pagina para que solo pueda actualizar una pagina a la vez ya que el codigo depende de la info en la tabla
            this.anterioresRegistrosBoton.setEnabled(false);
            this.siguientesRegistrosBoton.setEnabled(false);

        }else if (evt.getStateChange() == ItemEvent.DESELECTED){
            this.registrosTable.setEnabled(false);
            llenarTabla();
            //desactivo los botones para modificar los otros
            this.eliminarBoton.setEnabled(false);
            this.aplicarCambiosBoton.setEnabled(false);
            //rectivo los botones de cambiar pagina
            this.anterioresRegistrosBoton.setEnabled(true);
            this.siguientesRegistrosBoton.setEnabled(true);
            
        }
    }//GEN-LAST:event_modificarCheckBoxItemStateChanged

    private void eliminarBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarBotonActionPerformed
        System.out.println("" + (int)(this.registrosTable.getValueAt(this.registrosTable.getSelectedRow(), 0)) + 1);
        
        if(this.registrosTable.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(null, "SELECCIONE UN RENGLON");
            return;
        }
        
        ConexionSQL.eliminar(this.entidadComBox.getSelectedItem() + "", (int)(this.registrosTable.getValueAt(this.registrosTable.getSelectedRow(), 0)), dic);
        llenarTabla();
 
    }//GEN-LAST:event_eliminarBotonActionPerformed

    private void aplicarCambiosBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aplicarCambiosBotonActionPerformed
        //modifico cada renglon al que se le hizo click (se asume que lo modificaron)
        for (Integer renglon: renglonesClickeados) {
            if (renglon != -1){
                ConexionSQL.modificarUnRegistro(this.entidadComBox.getSelectedItem() + "", renglon, this.registrosTable, dic);
            }
        }
        
        llenarTabla();

    }//GEN-LAST:event_aplicarCambiosBotonActionPerformed

    private void registrosTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registrosTableMouseClicked
        if(evt.getClickCount() == 1){
            this.renglonesClickeados.add(this.registrosTable.getSelectedRow());
        }
         
        //no permitir cambiar las ids
        if(this.registrosTable.getSelectedColumn() == 0){
            this.modificarCheckBox.setSelected(false);
            JOptionPane.showMessageDialog(rootPane, "NO SE PUEDE MODIFICAR LAS IDS");
        }
    }//GEN-LAST:event_registrosTableMouseClicked

    private void nuevoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoBotonActionPerformed
                c = ConexionSQL.conectar();
        //-- Make sure to have an ORDER BY clause (even if it’s just a dummy one) OFFSET n - 1 ROWS FETCH NEXT m - n + 1 ROWS ONLY;
        Statement oSt;
        try {
            //hago una query para conseguir los nombres de las tablas
            oSt = (Statement) c.createStatement();
            ResultSet rs = oSt.executeQuery("SELECT * FROM " + this.entidadComBox.getSelectedItem() + " ORDER BY (SELECT NULL) " + "OFFSET " + 0 + " ROWS FETCH NEXT " + 1 + "ROWS ONLY");
            
            //ajusto el model
            DefaultTableModel model = new DefaultTableModel();
            ResultSetMetaData metaData = rs.getMetaData();
           
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                model.addColumn(columnName);
            }
            model.setRowCount(1);

        //creo crearFrame
        crearFrame f = new crearFrame(model, this.entidadComBox.getSelectedItem() + "");
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null, "ERROR DE SQL " + ex.toString());
        } finally {
            ConexionSQL.desconectar(c);
        }  
    }//GEN-LAST:event_nuevoBotonActionPerformed

    private void eliminarBoton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarBoton1ActionPerformed
        
        try {
            Export.Imprimir();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_eliminarBoton1ActionPerformed

    private void entidadComBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_entidadComBox1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_entidadComBox1ItemStateChanged

    private void entidadComBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entidadComBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_entidadComBox1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        llenarTabla();
    }//GEN-LAST:event_jTextField1KeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton anterioresRegistrosBoton;
    private javax.swing.JButton aplicarCambiosBoton;
    private javax.swing.JButton eliminarBoton;
    private javax.swing.JButton eliminarBoton1;
    private javax.swing.JComboBox<String> entidadComBox;
    private javax.swing.JComboBox<String> entidadComBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JCheckBox modificarCheckBox;
    private javax.swing.JButton nuevoBoton;
    private javax.swing.JTable registrosTable;
    private javax.swing.JButton siguientesRegistrosBoton;
    // End of variables declaration//GEN-END:variables
}
