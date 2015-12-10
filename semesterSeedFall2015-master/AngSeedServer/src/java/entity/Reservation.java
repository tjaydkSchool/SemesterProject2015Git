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
    @NamedQuery(name = "Reservation.findReservations", query = "SELECT r FROM Reservation r WHERE r.reserveEmail = :userName")})
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String reservationID;
    private int numberOfSeats;
    private String reserveName;
    private String reservePhone;
    private String ID;
    private String reserveEmail;
    @OneToMany
    private List<Passengers> Passengers;

    public Reservation() {
    }

    public Reservation(int numberOfSeats, String reserveName, String reservePhone, String id, List<Passengers> passengers) {
        this.numberOfSeats = numberOfSeats;
        this.reserveName = reserveName;
        this.reservePhone = reservePhone;
        this.ID = id;
        this.Passengers = passengers;
    }

    public Reservation(int numberOfSeats, String reserveName, String reservePhone, String id, String reserveEmail, List<Passengers> passengers) {
        this.numberOfSeats = numberOfSeats;
        this.reserveName = reserveName;
        this.reservePhone = reservePhone;
        this.ID = id;
        this.reserveEmail = reserveEmail;
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
        return reserveName;
    }

    public void setReserveName(String reserveName) {
        this.reserveName = reserveName;
    }

    public String getReservePhone() {
        return reservePhone;
    }

    public void setReservePhone(String reservePhone) {
        this.reservePhone = reservePhone;
    }

    public String getId() {
        return ID;
    }

    public void setId(String id) {
        this.ID = id;
    }

    public String getUser() {
        return reserveEmail;
    }

    public void setUser(String reserveEmail) {
        this.reserveEmail = reserveEmail;
    }

    public List<Passengers> getPassengers() {
        return Passengers;
    }

    public void setPassengers(List<Passengers> passengers) {
        this.Passengers = passengers;
    }
}
