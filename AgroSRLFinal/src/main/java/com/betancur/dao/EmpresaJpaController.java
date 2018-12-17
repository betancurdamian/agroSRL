/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betancur.dao;

import com.betancur.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.betancur.model.Campo;
import com.betancur.model.Empresa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ariel
 */
public class EmpresaJpaController implements Serializable {

    public EmpresaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresa empresa) {
        if (empresa.getListaCampos() == null) {
            empresa.setListaCampos(new ArrayList<Campo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Campo> attachedListaCampos = new ArrayList<Campo>();
            for (Campo listaCamposCampoToAttach : empresa.getListaCampos()) {
                listaCamposCampoToAttach = em.getReference(listaCamposCampoToAttach.getClass(), listaCamposCampoToAttach.getId());
                attachedListaCampos.add(listaCamposCampoToAttach);
            }
            empresa.setListaCampos(attachedListaCampos);
            em.persist(empresa);
            for (Campo listaCamposCampo : empresa.getListaCampos()) {
                Empresa oldUnaEmpresaOfListaCamposCampo = listaCamposCampo.getUnaEmpresa();
                listaCamposCampo.setUnaEmpresa(empresa);
                listaCamposCampo = em.merge(listaCamposCampo);
                if (oldUnaEmpresaOfListaCamposCampo != null) {
                    oldUnaEmpresaOfListaCamposCampo.getListaCampos().remove(listaCamposCampo);
                    oldUnaEmpresaOfListaCamposCampo = em.merge(oldUnaEmpresaOfListaCamposCampo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empresa empresa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa persistentEmpresa = em.find(Empresa.class, empresa.getId());
            List<Campo> listaCamposOld = persistentEmpresa.getListaCampos();
            List<Campo> listaCamposNew = empresa.getListaCampos();
            List<Campo> attachedListaCamposNew = new ArrayList<Campo>();
            for (Campo listaCamposNewCampoToAttach : listaCamposNew) {
                listaCamposNewCampoToAttach = em.getReference(listaCamposNewCampoToAttach.getClass(), listaCamposNewCampoToAttach.getId());
                attachedListaCamposNew.add(listaCamposNewCampoToAttach);
            }
            listaCamposNew = attachedListaCamposNew;
            empresa.setListaCampos(listaCamposNew);
            empresa = em.merge(empresa);
            for (Campo listaCamposOldCampo : listaCamposOld) {
                if (!listaCamposNew.contains(listaCamposOldCampo)) {
                    listaCamposOldCampo.setUnaEmpresa(null);
                    listaCamposOldCampo = em.merge(listaCamposOldCampo);
                }
            }
            for (Campo listaCamposNewCampo : listaCamposNew) {
                if (!listaCamposOld.contains(listaCamposNewCampo)) {
                    Empresa oldUnaEmpresaOfListaCamposNewCampo = listaCamposNewCampo.getUnaEmpresa();
                    listaCamposNewCampo.setUnaEmpresa(empresa);
                    listaCamposNewCampo = em.merge(listaCamposNewCampo);
                    if (oldUnaEmpresaOfListaCamposNewCampo != null && !oldUnaEmpresaOfListaCamposNewCampo.equals(empresa)) {
                        oldUnaEmpresaOfListaCamposNewCampo.getListaCampos().remove(listaCamposNewCampo);
                        oldUnaEmpresaOfListaCamposNewCampo = em.merge(oldUnaEmpresaOfListaCamposNewCampo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = empresa.getId();
                if (findEmpresa(id) == null) {
                    throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa empresa;
            try {
                empresa = em.getReference(Empresa.class, id);
                empresa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.", enfe);
            }
            List<Campo> listaCampos = empresa.getListaCampos();
            for (Campo listaCamposCampo : listaCampos) {
                listaCamposCampo.setUnaEmpresa(null);
                listaCamposCampo = em.merge(listaCamposCampo);
            }
            em.remove(empresa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empresa> findEmpresaEntities() {
        return findEmpresaEntities(true, -1, -1);
    }

    public List<Empresa> findEmpresaEntities(int maxResults, int firstResult) {
        return findEmpresaEntities(false, maxResults, firstResult);
    }

    private List<Empresa> findEmpresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresa.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Empresa findEmpresa(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empresa> rt = cq.from(Empresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
