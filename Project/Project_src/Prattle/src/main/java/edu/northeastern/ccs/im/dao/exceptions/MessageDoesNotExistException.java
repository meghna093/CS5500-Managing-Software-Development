package edu.northeastern.ccs.im.dao.exceptions;

/**
 * @author Akshat Shukla
 */
public class MessageDoesNotExistException extends RuntimeException {
    /** Generated serial version uid. */
    private static final long serialVersionUID = -2822265714929473145L;

    /**
     * Create a new instance of this exception giving an explicit messaging stating
     * why the exception was thrown.
     *
     * @param message Reason for which the program throws the exception.
     */
    public MessageDoesNotExistException (String message) {
        super(message);
    }
}
