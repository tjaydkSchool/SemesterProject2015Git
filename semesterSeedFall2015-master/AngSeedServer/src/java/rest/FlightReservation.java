/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Reservation;
import facades.ReservationFacade;
import java.io.IOException;
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
    @Path("{airlineName}")
    @Consumes("application/json")
    @Produces("application/json")
    public String CreateReservation(String reservation, @PathParam("airlineName") String airlineName) throws IOException {
        f.createReservation(gson.fromJson(reservation, Reservation.class));
        return f.updateSeats(reservation, gson.fromJson(reservation, Reservation.class), airlineName);
    }
}
