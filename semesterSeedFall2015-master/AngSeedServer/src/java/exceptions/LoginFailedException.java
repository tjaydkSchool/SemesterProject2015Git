/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Dennis
 */
public class LoginFailedException extends Exception {

    /**
     * Creates a new instance of <code>LoginFailedException</code> with
     * the detail message: "Username or Password is incorrect".
     */
    public LoginFailedException() {
        super("Username or Password is incorrect");
    }

    /**
     * Constructs an instance of <code>LoginFailedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LoginFailedException(String msg) {
        super(msg);
    }
}
