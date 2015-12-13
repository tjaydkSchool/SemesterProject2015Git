/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Ebbe
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Reservation.findReservations", query = "SELECT r FROM Reservation r WHERE r.ReserveeEmail = :userName"),
    @NamedQuery(name = "Reservation.findAll", query = "SELECT r FROM Reservation r")
})
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String reservationID;
    private int numberOfSeats;
    private String ReserveeName;
    private String ReservePhone;
    private String flightID;
    private String ReserveeEmail;
    private String origin;
    private String destination;
    @OneToMany
    private List<Passengers> Passengers;

    public Reservation() {
    }

    public Reservation(int numberOfSeats, String reserveName, String reservePhone, String id, List<Passengers> passengers) {
        this.numberOfSeats = numberOfSeats;
        this.ReserveeName = reserveName;
        this.ReservePhone = reservePhone;
        this.flightID = id;
        this.Passengers = passengers;
    }

    public Reservation(int numberOfSeats, String reserveName, String reservePhone, String id, String reserveEmail, List<Passengers> passengers) {
        this.numberOfSeats = numberOfSeats;
        this.ReserveeName = reserveName;
        this.ReservePhone = reservePhone;
        this.flightID = id;
        this.ReserveeEmail = reserveEmail;
        this.Passengers = passengers;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getReserveName() {
        return ReserveeName;
    }

    public void setReserveName(String reserveName) {
        this.ReserveeName = reserveName;
    }

    public String getReservePhone() {
        return ReservePhone;
    }

    public void setReservePhone(String reservePhone) {
        this.ReservePhone = reservePhone;
    }

    public String getId() {
        return flightID;
    }

    public void setId(String id) {
        this.flightID = id;
    }

    public String getUser() {
        return ReserveeEmail;
    }

    public void setUser(String reserveEmail) {
        this.ReserveeEmail = reserveEmail;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<Passengers> getPassengers() {
        return Passengers;
    }

    public void setPassengers(List<Passengers> passengers) {
        this.Passengers = passengers;
    }
}
