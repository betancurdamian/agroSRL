/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betancur.controller;


import com.betancur.dao.CampoJpaController;
import com.betancur.dao.Conexion;
import com.betancur.dao.EstadoCampoJpaController;
import com.betancur.dao.LoteJpaController;
import com.betancur.dao.TipoSueloJpaController;
import com.betancur.model.Campo;
import com.betancur.model.Empresa;
import com.betancur.model.EstadoCampo;
import com.betancur.model.Lote;
import com.betancur.model.TipoSuelo;
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
    private final LoteJpaController loteDAO = new LoteJpaController(Conexion.getEntityManagerFactory()); 
    private final TipoSueloJpaController tipoSueloDAO = new TipoSueloJpaController(Conexion.getEntityManagerFactory());
    private final EstadoCampoJpaController estadoCampoDAO = new EstadoCampoJpaController(Conexion.getEntityManagerFactory());
    
    public ControllerRegistrarCampo() {
        //Setea el controlador con una empresa. 
        //La empresa es un SINGLETON
        setEmpresaModel(ControllerEmpresa.getInstance().getEmpresaUnica());
    }
    
    /**
     * verificarNombreDeCampo
     * verifica que el nombre del campo que se ingresa, no se encuentre
     * registrado en la empresa
     * 
     * @param nombreCampo
     * @return 
     */    
    public boolean verificarNombreDeCampo(String nombreCampo){
        boolean nombreDeCampoValido = true;
        Campo campoAux = new Campo();        
        campoAux.setNombreCampo(nombreCampo);
        if (empresaModel!=null) {            
             for (Campo campoRecorrido : campoDAO.findCampoEntities()) {                
                if (campoRecorrido.getNombreCampo().equals(campoAux.getNombreCampo())) {
                    nombreDeCampoValido = false;
                }
            }   
        }else{
            System.out.println("No hay empresa ");
        }   
        return nombreDeCampoValido;
    }
    
    /**
     * verificarNumeroLote
     * Si en numero esta en la lista de lotes del campo, devuelve verdadero
     * @param unCampo
     * @param numeroLote
     * @return 
     */
    public boolean verificarNumeroLote(Campo unCampo, int numeroLote){
        boolean campoEncontrado = false;
        if(unCampo!=null){
            for (Lote recorridoLote : unCampo.getListaDeLotes()) {
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
            for (Lote recorridoLote : unCampo.getListaDeLotes()) {
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
    
    public boolean agregarTipoSueloAlLote(TipoSuelo ts, Lote l){
        boolean agregado = false;
        TipoSuelo auxTipoSuelo = null;
        for (TipoSuelo recorridoTipoSueloEntity : tipoSueloDAO.findTipoSueloEntities()) {
            if (recorridoTipoSueloEntity.getNumero()==ts.getNumero()) {
                auxTipoSuelo = recorridoTipoSueloEntity;
            }
        }
        if (auxTipoSuelo!=null) {
            l.setTipoSuelo(auxTipoSuelo);
            agregado = true;
        }
        return agregado;
    }
    
    
    public void finalizarRegistroNuevoCampo(Campo nuevoCampo){
        nuevoCampo.setEmpresa(empresaModel);
        
        //recupera el estabo bien de la base de datos        
        EstadoCampo unEstadoCampo = estadoCampoDAO.findEstadoCampo(1L);        
        
        nuevoCampo.setEstadoCampo(unEstadoCampo);
        
        if (nuevoCampo!=null) {
            if (nuevoCampo.getListaDeLotes().isEmpty()) {
                System.out.println("campo sin lotes");
            }else{
                System.out.println("campo con lotes"); 
                campoDAO = new CampoJpaController(Conexion.getEntityManagerFactory());
                campoDAO.create(nuevoCampo);
            }
        }
    }
    
    
    public Empresa getEmpresaModel() {
        return empresaModel;
    }

    private void setEmpresaModel(Empresa empresaModel) {
        this.empresaModel = empresaModel;
    }

    
    
    
    
}
