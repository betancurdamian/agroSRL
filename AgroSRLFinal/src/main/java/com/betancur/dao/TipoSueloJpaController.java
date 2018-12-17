/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betancur.dao;

import com.betancur.dao.exceptions.NonexistentEntityException;
import com.betancur.model.TipoSuelo;
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
public class TipoSueloJpaController implements Serializable {

    public TipoSueloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoSuelo tipoSuelo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoSuelo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoSuelo tipoSuelo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoSuelo = em.merge(tipoSuelo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoSuelo.getId();
                if (findTipoSuelo(id) == null) {
                    throw new NonexistentEntityException("The tipoSuelo with id " + id + " no longer exists.");
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
            TipoSuelo tipoSuelo;
            try {
                tipoSuelo = em.getReference(TipoSuelo.class, id);
                tipoSuelo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoSuelo with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoSuelo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoSuelo> findTipoSueloEntities() {
        return findTipoSueloEntities(true, -1, -1);
    }

    public List<TipoSuelo> findTipoSueloEntities(int maxResults, int firstResult) {
        return findTipoSueloEntities(false, maxResults, firstResult);
    }

    private List<TipoSuelo> findTipoSueloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoSuelo.class));
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

    public TipoSuelo findTipoSuelo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoSuelo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoSueloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoSuelo> rt = cq.from(TipoSuelo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
