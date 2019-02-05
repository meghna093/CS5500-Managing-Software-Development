package edu.northeastern.ccs.im.dao.exceptions;

/**
 * Exception for when the groupName searched for does not exist.
 */
public class GroupDoesNotExistException extends RuntimeException {
  /**
   * Generated serial version uid.
   */
  private static final long serialVersionUID = -2822265714929473143L;

  /**
   * Create a new instance of this exception giving an explicit messaging stating
   * why the exception was thrown.
   *
   * @param message Reason for which the program throws the exception.
   */
  public GroupDoesNotExistException(String message) {
    super(message);
  }
}

