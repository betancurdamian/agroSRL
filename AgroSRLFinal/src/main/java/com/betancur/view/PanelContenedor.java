/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betancur.view;

import javax.swing.JPanel;

/**
 *
 * @author Ariel
 */
public class PanelContenedor extends javax.swing.JPanel {
    private static PanelContenedor contenedorSingleton;
    /**
     * Creates new form PanelContenedor
     */
    private PanelContenedor() {
        initComponents();        
        
    }
    
    public static PanelContenedor getInstance(){
        if (contenedorSingleton == null) {
            contenedorSingleton = new PanelContenedor();
        }
        return contenedorSingleton;
    }

    public void cargarModulo(JPanel unPanel){
        this.removeAll();
        unPanel.setSize(800, 600);
        
        this.add(unPanel);   
        
        this.setVisible(true);
        //Mejor que repaint
        this.updateUI();
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
