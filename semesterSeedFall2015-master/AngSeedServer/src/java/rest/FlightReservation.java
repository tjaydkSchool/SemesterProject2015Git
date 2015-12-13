/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Reservation;
import exceptions.NoAvailableTicketsException;
import facades.ReservationFacade;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Ebbe
 */
@Path("flightreservation")
public class FlightReservation {

    ReservationFacade f = new ReservationFacade();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @POST
    @Path("{airlineName}/{from}/{to}")
    @Consumes("application/json")
    @Produces("application/json")
    public String CreateReservation(String reservation, @PathParam("airlineName") String airlineName, @PathParam("from") String from, @PathParam("to") String to) throws NoAvailableTicketsException, IOException {
            return f.updateSeats(reservation, gson.fromJson(reservation, Reservation.class), airlineName, from, to);
    }
}
