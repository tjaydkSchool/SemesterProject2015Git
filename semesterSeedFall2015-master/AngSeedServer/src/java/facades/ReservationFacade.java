/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import deploy.DeploymentConfiguration;
import entity.Passengers;
import entity.Reservation;
import exceptions.NoAvailableTicketsException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Ebbe
 */
public class ReservationFacade {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.puName);
    private EntityManager em = emf.createEntityManager();

    public void createReservation(Reservation reservation, String from, String to) {
        em.getTransaction().begin();
        for (int i = 0; i < reservation.getPassengers().size(); i++) {
            Passengers p = reservation.getPassengers().get(i);
            em.persist(p);
        }
        em.getTransaction().commit();
        Reservation res = reservation;

        res.setOrigin(from);
        res.setDestination(to);
        em.getTransaction().begin();
        em.persist(reservation);
        em.getTransaction().commit();
    }

    public String updateSeats(String reservation, Reservation res, String AirlineName, String from, String to) throws MalformedURLException, IOException, NoAvailableTicketsException {
        String urlToUse = "";

        Query query = em.createQuery("SELECT u.url from URL u WHERE u.airlineName = '" + AirlineName + "'");
        urlToUse = (String) query.getResultList().get(0) + "/api/flightreservation";
        URL url = new URL(urlToUse);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json;");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        try (OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream())) {
            out.write(reservation);
        }

        String jsonStr = "";
        try (Scanner scan = new Scanner(con.getInputStream())) {
            jsonStr = null;
            while (scan.hasNext()) {
                jsonStr = jsonStr + scan.nextLine();
            }
            createReservation(res, from, to);
            return jsonStr;
        } catch (IOException e) {
            throw new NoAvailableTicketsException();
        }
    }

    public static void main(String[] args) throws IOException, MalformedURLException, NoAvailableTicketsException {
        ReservationFacade f = new ReservationFacade();
        Passengers p1 = new Passengers("First", "Last");
        Passengers p2 = new Passengers("newFirst", "newLast");
        List<Passengers> pl = new ArrayList();
        pl.add(p1);
        pl.add(p2);
        
        Reservation r = new Reservation();
        r.setNumberOfSeats(5);
        r.setPassengers(pl);
        r.setId("100001");
        r.setReserveName("Navnet");
        r.setReservePhone("1234");
        r.setUser("test@test.dk");
        f.updateSeats("{\n"
                + "    \"numberOfSeats\": 5,\n"
                + "    \"ReserveeName\": \"Bubber\",\n"
                + "    \"ReservePhone\": \"12345678\",\n"
                + "    \"ReserveeEmail\": \"test@test.dk\",\n"
                + "    \"flightID\": \"100001\",\n"
                + "    \"Passengers\":[\n"
                + "        { \"firstName\":\"Peter\",\n"
                + "          \"lastName\": \"Peterson\"\n"
                + "        },\n"
                + "        { \"firstName\":\"Jane\",\n"
                + "          \"lastName\": \"Peterson\"\n"
                + "        }\n"
                + "    ]\n"
                + "}", r, "The Giant Horn Airline","CPH", "LHR");
    }
}
