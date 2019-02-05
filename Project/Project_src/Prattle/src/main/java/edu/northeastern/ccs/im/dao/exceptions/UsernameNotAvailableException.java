package edu.northeastern.ccs.im.dao.exceptions;

/**
 * Exception for when the username is not available.
 */
public class UsernameNotAvailableException extends RuntimeException {
  /** Generated serial version uid. */
  private static final long serialVersionUID = -2822265714929473148L;

  /**
   * Create a new instance of this exception giving an explicit messaging stating
   * why the exception was thrown.
   *
   * @param message Reason for which the program throws the exception.
   */
  public UsernameNotAvailableException (String message) {
    super(message);
  }
}
