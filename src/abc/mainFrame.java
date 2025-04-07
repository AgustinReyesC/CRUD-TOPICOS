/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package abc;

import com.lowagie.text.DocumentException;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
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

    
    //Nueva string para guardar el statement
    String currentStatement;
    
    public mainFrame() {
        initComponents();
        renglonesClickeados = new ArrayList();
        //desactivo los botones de modificar
        this.eliminarBoton.setEnabled(false);
        this.aplicarCambiosBoton.setEnabled(false);
        
        //desactivo los elementos de buscar
        this.jTextField1.setEnabled(false);
        this.entidadComBox1.setEnabled(false);
        
        mostrarDesdeNRegistros(this.entidadComBox.getSelectedItem() + "", 0, paginaDeRegistro * 23, false, "", "");
        
        //Crear el mapa para encontrar IDs
        dic = new HashMap<>();
        dic.put("ESTUDIANTES", "id");
        dic.put("MAESTROS", "id_maestro");
        dic.put("MATERIAS", "id_materia");
        
        
        currentStatement = "";
        
        
        
        //NUEVO DISEÑO DE LA TABLA
        JTableHeader header = registrosTable.getTableHeader();
        header.setFont(new Font("Candara", Font.PLAIN, 16));
        header.setBackground(new Color(200, 200, 200));
        header.setForeground(Color.BLACK);
        
        //Para centrar los encabezados
        ((DefaultTableCellRenderer) registrosTable.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        registrosTable.setGridColor(new Color(220, 220, 220));
    }

    
    //Le voy a agregar un apartado para ver si puedo hacer búsquedas
    
    public void mostrarDesdeNRegistros(String entidad, int inicio, int fin, boolean condition, String atributo, String value){
        c = ConexionSQL.conectar();
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
            
            currentStatement = creandoStatement;
            creandoStatement += " LIMIT " + offset + ", " + cuantos;
            
            
            
            
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
        
        dicBusqueda.put("ALUMNO", "nombre");
        dicBusqueda.put("PROMEDIO", "id_estudiante");
        dicBusqueda.put("PROFESOR", "nombre2");

        
        
        String entidad = entidadComBox.getSelectedItem().toString();
        String valorBuscado = jTextField1.getText();
        String atributo = dicBusqueda.get(entidadComBox1.getSelectedItem().toString());

        if (atributo != null && !valorBuscado.isEmpty()) {
            mostrarDesdeNRegistros(entidad, (paginaDeRegistro - 1) * 23, paginaDeRegistro * 23, true, atributo, valorBuscado);
        } else {
            // O mostrás todo, o mostrás un mensaje de advertencia
            mostrarDesdeNRegistros(entidad, (paginaDeRegistro - 1) * 23, paginaDeRegistro * 23, false, "", "");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        registrosTable = new javax.swing.JTable();
        anterioresRegistrosBoton = new javax.swing.JButton();
        siguientesRegistrosBoton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        entidadComBox = new javax.swing.JComboBox<>();
        nuevoBoton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        entidadComBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        buscarCheckBox = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        aplicarCambiosBoton = new javax.swing.JButton();
        modificarCheckBox = new javax.swing.JCheckBox();
        eliminarBoton = new javax.swing.JButton();
        eliminarBoton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        entidadComBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        entidadComBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ESTUDIANTES", "MAESTROS", "MATERIAS", " " }));
        entidadComBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                entidadComBoxItemStateChanged(evt);
            }
        });
        entidadComBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entidadComBoxActionPerformed(evt);
            }
        });

        nuevoBoton.setText("NUEVO");
        nuevoBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoBotonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nuevoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(entidadComBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(entidadComBox, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nuevoBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("BUSCAR POR:");

        entidadComBox1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        entidadComBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NOMBRE", "MATRICULA", "PROMEDIO" }));

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        buscarCheckBox.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        buscarCheckBox.setText("buscar");
        buscarCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                buscarCheckBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(entidadComBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(buscarCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(buscarCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(entidadComBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(aplicarCambiosBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(modificarCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(eliminarBoton)
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(eliminarBoton1)
                        .addGap(51, 51, 51))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(eliminarBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(eliminarBoton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(anterioresRegistrosBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(siguientesRegistrosBoton, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 775, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(anterioresRegistrosBoton)
                            .addComponent(siguientesRegistrosBoton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
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
           this.buscarCheckBox.setSelected(false);
        }
    }//GEN-LAST:event_entidadComBoxItemStateChanged

    private void buscarCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_buscarCheckBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED){
            //activo los botones para modificar
            this.entidadComBox1.setEnabled(true);
            this.jTextField1.setEnabled(true);
            //desactivo modificar
            this.modificarCheckBox.setSelected(false);
        }else if (evt.getStateChange() == ItemEvent.DESELECTED){
            this.registrosTable.setEnabled(false);
            llenarTabla();
            //desactivo los botones para modificar los otros
            this.entidadComBox1.setEnabled(false);
            this.jTextField1.setEnabled(false);
        }
    }//GEN-LAST:event_buscarCheckBoxItemStateChanged

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
            this.buscarCheckBox.setSelected(false);
            JOptionPane.showMessageDialog(rootPane, "NO SE PUEDE MODIFICAR LAS IDS");
        }
    }//GEN-LAST:event_registrosTableMouseClicked

    private void nuevoBotonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevoBotonActionPerformed
        c = ConexionSQL.conectar();
        Statement oSt;
        try {
            //hago una query para conseguir los nombres de las tablas
            oSt = (Statement) c.createStatement();
            ResultSet rs = oSt.executeQuery("SELECT * FROM " + this.entidadComBox.getSelectedItem() + " LIMIT 0, 1");

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

    //funcion conseguida de: https://es.stackoverflow.com/questions/171179/como-validar-si-un-campo-de-texto-es-numerico-en-java?utm_source=chatgpt.com
    public static boolean isNumeric(String str) {
     try { 
        Double.parseDouble(str); 
     } catch(NumberFormatException e) { 
        return false; 
     }
     return true;

    }
    
    private void eliminarBoton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarBoton1ActionPerformed
        
        try {
            Export.Imprimir(currentStatement);
        } catch (FileNotFoundException | DocumentException | SQLException ex) {
            Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_eliminarBoton1ActionPerformed
    
    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        //llenarTabla();
        //try
            //incia la conexion
            // Haz la unión triple (en realidad, cuádruple)
                //crea la string
                //le pone la sintaxis de sql
            // Agrega la condición correcta para el elemento seleccionado
            // Agrega la declaración a la variable que imprimir necesita
            // Agrega las restricciones para cuántos se van a mostrar en la tabla (con los botones)
            // Ejecutarlo

       //except
            //sql exception
                //cerrar la conexion
        
        ((DefaultTableModel)this.registrosTable.getModel()).setRowCount(0);
        
        String preparedString = "";
        try{
            c = ConexionSQL.conectar();
            Statement oSt;
            
            //statement inicial, un cuadruple join para conseguir todos los datos
            preparedString = "SELECT concat(m.nombre1, ' ', m.nombre2, ' ', m.apellido_paterno, ' ', m.apellido_materno)"
                    + " as nombre_maestro, mat.nombre as materia, e.nombre as nombre_alumno, e.matricula, ag.calificacion, e.promedio FROM "
                    + "maestros m join materias mat join grupo g join alumno_grupo ag join estudiantes e on m.id_maestro = g.id_maestro "
                    + "and mat.id_materia = g.id_materia and ag.id_grupo = g.id_grupo and ag.id_ESTUDIANTE = e.id";
            
            switch(this.entidadComBox1.getSelectedIndex()){
                case 0:
                    preparedString += " where concat(m.nombre1, ' ', m.nombre2, ' ', m.apellido_paterno, ' ',"
                            + " m.apellido_materno) like '" + this.jTextField1.getText() + "%' or e.nombre like '" + this.jTextField1.getText() + "%'";
                    
                    break;
                    
                case 1:
                    preparedString += " where e.matricula like '" + this.jTextField1.getText() + "%'";
                    
                    break;
                    
                case 2:
                    if(this.jTextField1.getText().isBlank()){
                        return;
                    }
                    if (!isNumeric(this.jTextField1.getText())){
                        JOptionPane.showMessageDialog(rootPane, "PARA BUSCAR POR PROMEDIO EL VALOR DEBE SER UN NÚMERO");
                    }
                    preparedString += " where abs(e.promedio - " + this.jTextField1.getText() + ") <= 0.4";
                    break;
            }
            
            //añadir que solo muestre los que caben en la tabla (solo muestra desde m hasta n
            int m = (paginaDeRegistro - 1) * 23;
            int n = paginaDeRegistro * 23;
            
            preparedString += " LIMIT " + (n  - m + 1)  + " OFFSET " + m + ";";
            
            //ejecutar el statement
            System.out.println(preparedString);
            oSt = (Statement) c.createStatement();
            ResultSet rs = oSt.executeQuery(preparedString);
            
            //lleno la tabla con esto
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
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            currentStatement = preparedString;
        }
                
        // En los botones de flecha
            //Elige una de las 2 funciones dependiendo de si 'buscarComboBox' está seleccionado
    }//GEN-LAST:event_jTextField1KeyReleased

    private void entidadComBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entidadComBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_entidadComBoxActionPerformed

    private void modificarCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_modificarCheckBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED){
            this.registrosTable.setEnabled(true);
            //activo los botones para modificar
            this.eliminarBoton.setEnabled(true);
            this.aplicarCambiosBoton.setEnabled(true);
            //desactivo los botones de cambiar pagina para que solo pueda actualizar una pagina a la vez ya que el codigo depende de la info en la tabla
            this.anterioresRegistrosBoton.setEnabled(false);
            this.siguientesRegistrosBoton.setEnabled(false);
            //desactivo buscar
            this.buscarCheckBox.setSelected(false);
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


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton anterioresRegistrosBoton;
    private javax.swing.JButton aplicarCambiosBoton;
    private javax.swing.JCheckBox buscarCheckBox;
    private javax.swing.JButton eliminarBoton;
    private javax.swing.JButton eliminarBoton1;
    private javax.swing.JComboBox<String> entidadComBox;
    private javax.swing.JComboBox<String> entidadComBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JCheckBox modificarCheckBox;
    private javax.swing.JButton nuevoBoton;
    private javax.swing.JTable registrosTable;
    private javax.swing.JButton siguientesRegistrosBoton;
    // End of variables declaration//GEN-END:variables
}
