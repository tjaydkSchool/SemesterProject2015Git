/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import deploy.DeploymentConfiguration;
import entity.Reservation;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Ebbe
 */
public class AdminFacade {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.puName);
    private EntityManager em = emf.createEntityManager();
    
    public List<Reservation> getReservations() {
        Query query = em.createNamedQuery("Reservation.findAll");
        return query.getResultList();
    }
}
