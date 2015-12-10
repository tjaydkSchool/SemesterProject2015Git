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
public class IlligalInputException extends Exception {

    public IlligalInputException() {
        super("That input type is not allowed");
    }

    public IlligalInputException(String msg) {
        super(msg);
    }
    
    
}
