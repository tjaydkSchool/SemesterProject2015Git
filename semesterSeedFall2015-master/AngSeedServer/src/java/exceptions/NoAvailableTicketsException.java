/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Ebbe
 */
public class NoAvailableTicketsException extends Exception {
    
    public NoAvailableTicketsException() {
        super("There are not enough available tickets for this flight");
    }
    
    public NoAvailableTicketsException(String msg) {
        super(msg);
    }
    
}
