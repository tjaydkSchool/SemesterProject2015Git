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
public class UnprocessableEntityError extends Exception {

    public UnprocessableEntityError() {
        super("Unprocessable Entity Error");
    }

    public UnprocessableEntityError(String msg) {
        super(msg);
    }

}
