/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betancur.controller;


import com.betancur.dao.CampoJpaController;
import com.betancur.dao.Conexion;
import com.betancur.dao.EmpresaJpaController;
import com.betancur.dao.EstadoCampoJpaController;
import com.betancur.dao.LoteJpaController;
import com.betancur.dao.TipoSueloJpaController;
import com.betancur.model.Campo;
import com.betancur.model.Empresa;
import com.betancur.model.EstadoCampo;
import com.betancur.model.Lote;
import com.betancur.model.TipoSuelo;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ariel
 */
public class ControllerRegistrarCampo {
    //Model
    private Empresa empresaModel;
    private CampoJpaController campoDAO = new CampoJpaController(Conexion.getEntityManagerFactory());   
    private LoteJpaController loteDAO = new LoteJpaController(Conexion.getEntityManagerFactory()); 
    private TipoSueloJpaController tipoSueloDAO = new TipoSueloJpaController(Conexion.getEntityManagerFactory());
    private EstadoCampoJpaController estadoCampoDAO = new EstadoCampoJpaController(Conexion.getEntityManagerFactory());
    
    public ControllerRegistrarCampo() {
        
        setEmpresaModel(ControllerEmpresa.getInstance().getEmpresaUnica());
    }
        
    public boolean verificarNombreDeCampo(String nombreCampo){
        boolean campoEncontrado = false;
        Campo campoAux = new Campo();        
        campoAux.setNombreCampo(nombreCampo);
        if (empresaModel!=null) {
             for (Campo campoRecorrido : campoDAO.findCampoEntities()) {
                if (campoRecorrido.getNombreCampo().equals(campoAux.getNombreCampo())) {
                    campoEncontrado = false;
                }else{
                    campoEncontrado = true;
                }
            }   
        }   
        return campoEncontrado;
    }
    
    /**
     * verificarNumeroLote
     * Si en numero esta en la lista de lotes del campo, devuelve verdadero
     * @param unCampo
     * @return 
     */
    public boolean verificarNumeroLote(Campo unCampo, int numeroLote){
        boolean campoEncontrado = false;
        if(unCampo!=null){
            for (Lote recorridoLote : unCampo.getListaLotes()) {
                if (recorridoLote.getNumeroLote()== numeroLote) {
                    campoEncontrado = true;
                }
            }
        }
        return campoEncontrado;
    }
    
   
    /**
     * Verificar Superficie de Lote
     * 
     * Verificar la superficie disponible del campo
     * recorriendo cada lote del mismo
     * @param unCampo
     * @return 
     */
    public float verificarSuperficieLote(Campo unCampo){
        float superficieRestante = 0;
        float superficieAuxiliar = 0;
        float superficieVerificada = 0;
        if(unCampo!=null){
            superficieRestante = unCampo.getSuperficieCampo();
            for (Lote recorridoLote : unCampo.getListaLotes()) {
                superficieAuxiliar = superficieAuxiliar + recorridoLote.getSuperficieLote();
            }
            superficieRestante = superficieRestante - superficieAuxiliar;
            
            if (superficieRestante>0) {
                superficieVerificada = superficieRestante;
            }
        }
        return superficieVerificada;
    }
    
    public List<TipoSuelo> getTipoSuelo(){
        List<TipoSuelo> tiposDeSuelo = new ArrayList<>();
        
        for (TipoSuelo recorridoTipoSueloEntity : tipoSueloDAO.findTipoSueloEntities()) {
            tiposDeSuelo.add(recorridoTipoSueloEntity);
        }
        
        return tiposDeSuelo;
    }
    
    public void finalizarRegistroNuevoCampo(Campo nuevoCampo){
        
        EstadoCampo unEstadoCampo = estadoCampoDAO.findEstadoCampo(1L);
        nuevoCampo.setUnEstadoCampo(unEstadoCampo);
        nuevoCampo.setUnaEmpresa(empresaModel);
        System.out.println("campo");
        System.out.println("id: "+nuevoCampo.getId());
        System.out.println("nombre: "+nuevoCampo.getNombreCampo());
        System.out.println("nombre: "+nuevoCampo.getSuperficieCampo());
        System.out.println("Empresa:"+ nuevoCampo.getUnaEmpresa().getRazonSocial());
        System.out.println("Lotes");
        
        for (Lote listaLote : nuevoCampo.getListaLotes()) {
            System.out.println("numero lote: "+listaLote.getNumeroLote());
            System.out.println("superficie lote: "+listaLote.getSuperficieLote());
            System.out.println("campo lote: "+listaLote.getUnCampo().getNombreCampo());
            System.out.println("tipo suelo lote: "+listaLote.getUnTipoSuelo().getDescripcion());
        }
        if (nuevoCampo!=null) {
            if (nuevoCampo.getListaLotes().isEmpty()) {
                
            }else{
                
                campoDAO.create(nuevoCampo);
                
            }
        }
        
        System.out.println("campo registrado");
    }
    
    
    public Empresa getEmpresaModel() {
        return empresaModel;
    }

    private void setEmpresaModel(Empresa empresaModel) {
        this.empresaModel = empresaModel;
    }

    
    
    
    
}
