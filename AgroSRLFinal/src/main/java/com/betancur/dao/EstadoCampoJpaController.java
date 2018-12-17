/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betancur.dao;

import com.betancur.dao.exceptions.NonexistentEntityException;
import com.betancur.model.EstadoCampo;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Ariel
 */
public class EstadoCampoJpaController implements Serializable {

    public EstadoCampoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoCampo estadoCampo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(estadoCampo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoCampo estadoCampo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            estadoCampo = em.merge(estadoCampo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = estadoCampo.getId();
                if (findEstadoCampo(id) == null) {
                    throw new NonexistentEntityException("The estadoCampo with id " + id + " no longer exists.");
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
            EstadoCampo estadoCampo;
            try {
                estadoCampo = em.getReference(EstadoCampo.class, id);
                estadoCampo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoCampo with id " + id + " no longer exists.", enfe);
            }
            em.remove(estadoCampo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoCampo> findEstadoCampoEntities() {
        return findEstadoCampoEntities(true, -1, -1);
    }

    public List<EstadoCampo> findEstadoCampoEntities(int maxResults, int firstResult) {
        return findEstadoCampoEntities(false, maxResults, firstResult);
    }

    private List<EstadoCampo> findEstadoCampoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoCampo.class));
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

    public EstadoCampo findEstadoCampo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoCampo.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCampoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoCampo> rt = cq.from(EstadoCampo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
