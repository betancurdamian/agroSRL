/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betancur.dao;

import com.betancur.dao.exceptions.NonexistentEntityException;
import com.betancur.model.Campo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.betancur.model.Empresa;
import com.betancur.model.Lote;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ariel
 */
public class CampoJpaController implements Serializable {

    public CampoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Campo campo) {
        if (campo.getListaLotes() == null) {
            campo.setListaLotes(new ArrayList<>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa unaEmpresa = campo.getUnaEmpresa();
            if (unaEmpresa != null) {
                unaEmpresa = em.getReference(unaEmpresa.getClass(), unaEmpresa.getId());
                campo.setUnaEmpresa(unaEmpresa);
            }
            List<Lote> attachedListaLotes = new ArrayList<>();
            for (Lote listaLotesLoteToAttach : campo.getListaLotes()) {
                listaLotesLoteToAttach = em.getReference(listaLotesLoteToAttach.getClass(), listaLotesLoteToAttach.getId());
                attachedListaLotes.add(listaLotesLoteToAttach);
            }
            campo.setListaLotes(attachedListaLotes);
            em.persist(campo);
            if (unaEmpresa != null) {
                unaEmpresa.getListaCampos().add(campo);
                unaEmpresa = em.merge(unaEmpresa);
            }
            for (Lote listaLotesLote : campo.getListaLotes()) {
                Campo oldUnCampoOfListaLotesLote = listaLotesLote.getUnCampo();
                listaLotesLote.setUnCampo(campo);
                listaLotesLote = em.merge(listaLotesLote);
                if (oldUnCampoOfListaLotesLote != null) {
                    oldUnCampoOfListaLotesLote.getListaLotes().remove(listaLotesLote);
                    oldUnCampoOfListaLotesLote = em.merge(oldUnCampoOfListaLotesLote);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Campo campo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Campo persistentCampo = em.find(Campo.class, campo.getId());
            Empresa unaEmpresaOld = persistentCampo.getUnaEmpresa();
            Empresa unaEmpresaNew = campo.getUnaEmpresa();
            List<Lote> listaLotesOld = persistentCampo.getListaLotes();
            List<Lote> listaLotesNew = campo.getListaLotes();
            if (unaEmpresaNew != null) {
                unaEmpresaNew = em.getReference(unaEmpresaNew.getClass(), unaEmpresaNew.getId());
                campo.setUnaEmpresa(unaEmpresaNew);
            }
            List<Lote> attachedListaLotesNew = new ArrayList<Lote>();
            for (Lote listaLotesNewLoteToAttach : listaLotesNew) {
                listaLotesNewLoteToAttach = em.getReference(listaLotesNewLoteToAttach.getClass(), listaLotesNewLoteToAttach.getId());
                attachedListaLotesNew.add(listaLotesNewLoteToAttach);
            }
            listaLotesNew = attachedListaLotesNew;
            campo.setListaLotes(listaLotesNew);
            campo = em.merge(campo);
            if (unaEmpresaOld != null && !unaEmpresaOld.equals(unaEmpresaNew)) {
                unaEmpresaOld.getListaCampos().remove(campo);
                unaEmpresaOld = em.merge(unaEmpresaOld);
            }
            if (unaEmpresaNew != null && !unaEmpresaNew.equals(unaEmpresaOld)) {
                unaEmpresaNew.getListaCampos().add(campo);
                unaEmpresaNew = em.merge(unaEmpresaNew);
            }
            for (Lote listaLotesOldLote : listaLotesOld) {
                if (!listaLotesNew.contains(listaLotesOldLote)) {
                    listaLotesOldLote.setUnCampo(null);
                    listaLotesOldLote = em.merge(listaLotesOldLote);
                }
            }
            for (Lote listaLotesNewLote : listaLotesNew) {
                if (!listaLotesOld.contains(listaLotesNewLote)) {
                    Campo oldUnCampoOfListaLotesNewLote = listaLotesNewLote.getUnCampo();
                    listaLotesNewLote.setUnCampo(campo);
                    listaLotesNewLote = em.merge(listaLotesNewLote);
                    if (oldUnCampoOfListaLotesNewLote != null && !oldUnCampoOfListaLotesNewLote.equals(campo)) {
                        oldUnCampoOfListaLotesNewLote.getListaLotes().remove(listaLotesNewLote);
                        oldUnCampoOfListaLotesNewLote = em.merge(oldUnCampoOfListaLotesNewLote);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = campo.getId();
                if (findCampo(id) == null) {
                    throw new NonexistentEntityException("The campo with id " + id + " no longer exists.");
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
            Campo campo;
            try {
                campo = em.getReference(Campo.class, id);
                campo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The campo with id " + id + " no longer exists.", enfe);
            }
            Empresa unaEmpresa = campo.getUnaEmpresa();
            if (unaEmpresa != null) {
                unaEmpresa.getListaCampos().remove(campo);
                unaEmpresa = em.merge(unaEmpresa);
            }
            List<Lote> listaLotes = campo.getListaLotes();
            for (Lote listaLotesLote : listaLotes) {
                listaLotesLote.setUnCampo(null);
                listaLotesLote = em.merge(listaLotesLote);
            }
            em.remove(campo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Campo> findCampoEntities() {
        return findCampoEntities(true, -1, -1);
    }

    public List<Campo> findCampoEntities(int maxResults, int firstResult) {
        return findCampoEntities(false, maxResults, firstResult);
    }

    private List<Campo> findCampoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Campo.class));
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

    public Campo findCampo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Campo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCampoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Campo> rt = cq.from(Campo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
