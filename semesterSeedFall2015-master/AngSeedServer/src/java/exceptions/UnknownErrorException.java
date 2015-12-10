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
public class UnknownErrorException extends Exception{

    public UnknownErrorException() {
        super("An Unknown Error occured");
    }

    public UnknownErrorException(String msg) {
        super(msg);
    }
    
}
