/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betancur.controller;

import com.betancur.model.Empresa;

/**
 *
 * @author Ariel
 */
public class ControllerEmpresa {
    private static ControllerEmpresa empresaSingleton;
    private Empresa empresaUnica;

    private ControllerEmpresa(){
    
    }
    
     //Aplicacion patron Songleton
    public static ControllerEmpresa getInstance() {
        if (empresaSingleton == null) {
            empresaSingleton = new ControllerEmpresa();
        }
        return empresaSingleton;
    }

    public Empresa getEmpresaUnica() {
        return empresaUnica;
    }

    public void setEmpresaUnica(Empresa empresaUnica) {
        this.empresaUnica = empresaUnica;
    }

  
    
}
