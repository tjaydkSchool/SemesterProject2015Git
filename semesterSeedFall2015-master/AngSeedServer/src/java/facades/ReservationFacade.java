/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Passengers;
import entity.Reservation;
import exceptions.NoAvailableTicketsException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    public String updateSeats(String reservation, Reservation res, String AirlineName) throws MalformedURLException, IOException, NoAvailableTicketsException {
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
            createReservation(res);
            return jsonStr;
        } catch (IOException e) {
            throw new NoAvailableTicketsException();
        }
    }

    public static void main(String[] args) throws IOException, MalformedURLException, NoAvailableTicketsException {
        ReservationFacade f = new ReservationFacade();
        Reservation reservation = new Reservation();
        f.updateSeats("{\n"
                + "    \"numberOfSeats\": 150,\n"
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
                + "}", reservation, "The Giant Horn Airline");
    }
}
