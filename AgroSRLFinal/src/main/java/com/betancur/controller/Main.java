/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betancur.controller;

import com.betancur.dao.Conexion;
import com.betancur.dao.EmpresaJpaController;
import com.betancur.model.Empresa;
import com.betancur.view.JFramePrincipal;

/**
 *
 * @author Ariel
 */
public class Main {
    public static void main(String[] args) {
        
        EmpresaJpaController empresaDAO = new EmpresaJpaController(Conexion.getEntityManagerFactory());
        Empresa empresaModel;
        empresaModel= empresaDAO.findEmpresa(1L);    
        ControllerEmpresa.getInstance().setEmpresaUnica(empresaModel);
        
        
        JFramePrincipal vista = new JFramePrincipal();
        
    }
}
