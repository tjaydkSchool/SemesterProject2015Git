/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Log;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Ebbe
 */
public class LogFacade {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SemesterProjectDbTestPU");
    private EntityManager em = emf.createEntityManager();

    public void LogDestination(String to) {
        Log log = em.find(Log.class, to);

        if (log == null) {
            Log newLog = new Log(to, 1);
            em.getTransaction().begin();
            em.persist(newLog);
            em.getTransaction().commit();
        } else {
            log.setCount(log.getCount() + 1);
            em.getTransaction().begin();
            em.merge(log);
            em.getTransaction().commit();
        }
    }
    
    public List getHotDestinations() {
        Query query = em.createNamedQuery("Log.findHotDestinations");
        query.setMaxResults(3);
        return query.getResultList();
    }
}
