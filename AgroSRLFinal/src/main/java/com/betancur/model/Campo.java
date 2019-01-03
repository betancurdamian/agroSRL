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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Ariel
 */
@Entity
@Table(name = "campo")
public class Campo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombreCampo")
    private String nombreCampo;
    
    @Column(name = "superficieCampo", length = 10)
    private float superficieCampo;
           
    @ManyToOne
    @JoinColumn(name="id_estadoCampo", nullable = false)
    private EstadoCampo estadoCampo;
    
    @ManyToOne
    @JoinColumn(name="id_empresa", nullable = false)
    private Empresa empresa; 
    
    @OneToMany(mappedBy = "campo", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Lote> listaDeLotes;

    public Campo() {
        //Se debe de inicializar la lista, para evitar nullPinterException
        listaDeLotes = new ArrayList<>();
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

    public EstadoCampo getEstadoCampo() {
        return estadoCampo;
    }

    public void setEstadoCampo(EstadoCampo estadoCampo) {
        this.estadoCampo = estadoCampo;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Lote> getListaDeLotes() {
        return listaDeLotes;
    }

    public void setListaDeLotes(List<Lote> listaDeLotes) {
        this.listaDeLotes = listaDeLotes;
    }

    
}
