package edu.northeastern.ccs.im.dao.exceptions;

public class RelationshipException extends RuntimeException{
    /**
     * Generated serial version uid.
     */
    private static final long serialVersionUID = -2822265714929473146L;

    /**
     * Create a new instance of this exception giving an explicit messaging stating
     * why the exception was thrown.
     *
     * @param message Reason for which the program throws the exception.
     */
    public RelationshipException(String message) {
        super(message);
    }
}
