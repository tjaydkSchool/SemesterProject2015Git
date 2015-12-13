/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Ebbe
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Log.findHotDestinations", query = "SELECT l.destination FROM Log l ORDER BY l.count DESC")
})
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String destination;
    private int count;

    public Log() {
    }

    public Log(String destination) {
        this.destination = destination;
    }

    public Log(String destination, int count) {
        this.destination = destination;
        this.count = count;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
