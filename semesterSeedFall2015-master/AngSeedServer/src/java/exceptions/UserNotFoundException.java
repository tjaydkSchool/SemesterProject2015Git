package exceptions;

/**
 *
 * @author Ebbe
 */
public class UserNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>UserNotFoundException</code> with the
     * detail message: "There is no user with the given username".
     */
    public UserNotFoundException() {
        super("There is no user with the given username");
    }

    /**
     * Constructs an instance of <code>UserNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UserNotFoundException(String msg) {
        super(msg);
    }

}
