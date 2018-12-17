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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Ariel
 */
@Entity
@Table(name = "EMPRESA")
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "razonSocial")
    private String razonSocial;

    @Column(name = "cuit")
    private String cuit;
    
    @Column(name = "unaDireccion")
    private String unaDireccion;
    
    //Campos que posee la empresa
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unaEmpresa")
    private List<Campo> listaCampos;

    public Empresa() {
        this.listaCampos = new ArrayList(); 
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
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.betancur.model.Empresa[ id=" + id + " ]";
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getUnaDireccion() {
        return unaDireccion;
    }

    public void setUnaDireccion(String unaDireccion) {
        this.unaDireccion = unaDireccion;
    }

    public List<Campo> getListaCampos() {
        return listaCampos;
    }

    public void setListaCampos(List<Campo> attachedListaCampos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
