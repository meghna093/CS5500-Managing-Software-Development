package edu.northeastern.ccs.im.dao.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Junit test class for UserDoesNotExistException.
 *
 * @author Matthew Lazarcheck
 */
class UserDoesNotExistExceptionTest {
  /**
   * Test UserDoesNotExistException exception.
   */
  @Test
  void UserDoesNotExistExceptionTest() {
    Assertions.assertThrows(UserDoesNotExistException.class, () -> {
      throw new UserDoesNotExistException("Test.");
    });
  }
}