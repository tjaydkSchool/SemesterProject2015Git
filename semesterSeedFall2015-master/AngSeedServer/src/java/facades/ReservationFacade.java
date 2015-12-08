/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Passengers;
import entity.Reservation;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Ebbe
 */
public class ReservationFacade {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SemesterProjectDbTestPU");
    private EntityManager em = emf.createEntityManager();

    public void createReservation(Reservation reservation) {
        em.getTransaction().begin();       
        for (int i = 0; i < reservation.getPassengers().size(); i++) {
            Passengers p = reservation.getPassengers().get(i);
            em.persist(p);
        }        
        em.getTransaction().commit();
        
        em.getTransaction().begin();
        em.persist(reservation);
        em.getTransaction().commit();
    }
    
    public void updateSeats(String reservation, String AirlineName) throws MalformedURLException, IOException {
        String urlToUse = "";
        
        Query query = em.createQuery("SELECT u.url from URL u WHERE u.airlineName = '" + AirlineName + "'");
        urlToUse = (String) query.getResultList().get(0) + "/api/flightreservation";
        URL url = new URL(urlToUse);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("PUT");
        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
        out.write(reservation);
        out.close();
        con.getInputStream();
    }

    public static void main(String[] args) throws IOException {
        ReservationFacade f = new ReservationFacade();
        List<Passengers> passengers = new ArrayList();
        Passengers p1 = new Passengers("Hans", "Lort");
        Passengers p2 = new Passengers("Jens", "Lort");
        passengers.add(p1);
        passengers.add(p2);
        Reservation r = new Reservation(3, "Bubber", "12345678", "100004", "test@test.dk", passengers);
        f.createReservation(r);
        f.updateSeats("{\n" +
"  \"numberOfSeats\": 3,\n" +
"  \"reserveName\": \"Bubber\",\n" +
"  \"reservePhone\": \"12345678\",\n" +
"  \"reserveEmail\": \"test@test.dk\",\n" +
"  \"ID\": \"100004\",\n" +
"  \"Passengers\": [\n" +
"    {\n" +
"      \"firstName\": \"Peter\",\n" +
"      \"lastName\": \"Peterson\"\n" +
"    },\n" +
"    {\n" +
"      \"firstName\": \"Jane\",\n" +
"      \"lastName\": \"Peterson\"\n" +
"    }\n" +
"  ]\n" +
"}", "The Giant Horn Airline");
    }

}
