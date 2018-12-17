/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betancur.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Ariel
 */
@Entity
@Table(name = "CAMPO")
public class Campo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "nombreCampo")
    private String nombreCampo;
    
    @Column(name = "superficieCampo", length = 10)
    private float superficieCampo;
    
    //Lotes que posee el campo
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unCampo")
    private List<Lote> listaLotes;
    
    @ManyToOne
    private EstadoCampo unEstadoCampo;
    
    @ManyToOne
    private Empresa unaEmpresa;

    public Campo() {
        this.listaLotes = new ArrayList(); 
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Campo)) {
            return false;
        }
        Campo other = (Campo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.betancur.model.Campo[ id=" + id + " ]";
    }

    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }

    public float getSuperficieCampo() {
        return superficieCampo;
    }

    public void setSuperficieCampo(float superficieCampo) {
        this.superficieCampo = superficieCampo;
    }

    public EstadoCampo getUnEstadoCampo() {
        return unEstadoCampo;
    }

    public void setUnEstadoCampo(EstadoCampo unEstadoCampo) {
        this.unEstadoCampo = unEstadoCampo;
    }

    public Empresa getUnaEmpresa() {
        return unaEmpresa;
    }

    public void setUnaEmpresa(Empresa unaEmpresa) {
        this.unaEmpresa = unaEmpresa;
    }

    public List<Lote> getListaLotes() {
        return listaLotes;
    }

    public void setListaLotes(List<Lote> attachedListaLotes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
