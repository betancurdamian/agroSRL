/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betancur.view;

import com.betancur.controller.ControllerRegistrarCampo;

/**
 *
 * @author Ariel
 */
public class JFramePrincipal extends javax.swing.JFrame {
    PanelContenedor contenedorSingleton;
    ModuloPrincipal moduloPrincipal;
    /**
     * Creates new form JFramePrincipal
     */
    public JFramePrincipal() {
        initComponents();
        this.setTitle("Registrar Campo");
        this.setSize(800, 630);
        this.setLocationRelativeTo(null);
        
        moduloPrincipal = new ModuloPrincipal();
                
                
        contenedorSingleton = PanelContenedor.getInstance();
        contenedorSingleton.setSize(800, 600);
        contenedorSingleton.setVisible(true);
        contenedorSingleton.cargarModulo(moduloPrincipal);
        
        this.add(contenedorSingleton);       
        
        
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.repaint();
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 639, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
