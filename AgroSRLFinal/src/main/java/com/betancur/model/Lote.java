/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betancur.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Ariel
 */
@Entity
@Table(name = "LOTE")
public class Lote implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "numeroLote", length = 10)
    private int numeroLote;
    
    @Column(name = "superficieLote", length = 10)
    private float superficieLote;
    
    @ManyToOne
    private TipoSuelo unTipoSuelo;
    
    @ManyToOne
    private Campo unCampo;
    
    
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
        if (!(object instanceof Lote)) {
            return false;
        }
        Lote other = (Lote) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.betancur.model.Lote[ id=" + id + " ]";
    }

    public int getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(int numeroLote) {
        this.numeroLote = numeroLote;
    }

    public float getSuperficieLote() {
        return superficieLote;
    }

    public void setSuperficieLote(float superficieLote) {
        this.superficieLote = superficieLote;
    }

    public TipoSuelo getUnTipoSuelo() {
        return unTipoSuelo;
    }

    public void setUnTipoSuelo(TipoSuelo unTipoSuelo) {
        this.unTipoSuelo = unTipoSuelo;
    }

    public Campo getUnCampo() {
        return unCampo;
    }

    public void setUnCampo(Campo unCampo) {
        this.unCampo = unCampo;
    }
    
    
}
