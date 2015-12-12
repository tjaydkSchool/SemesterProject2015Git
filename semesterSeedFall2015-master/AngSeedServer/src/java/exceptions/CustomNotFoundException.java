package exceptions;

/**
 *
 * @author Ebbe
 */
public class CustomNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>UserNotFoundException</code> with the
     * detail message: "There is no user with the given username".
     */
    public CustomNotFoundException() {
        super("There is no user with the given username");
    }

    /**
     * Constructs an instance of <code>UserNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CustomNotFoundException(String msg) {
        super(msg);
    }

}
