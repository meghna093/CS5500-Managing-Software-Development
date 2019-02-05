package edu.northeastern.ccs.im.dao.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Junit test class for UsernameNotAvailableException.
 *
 * @author Matthew Lazarcheck
 */
class UsernameNotAvailableExceptionTest {
  /**
   * Test UsernameNotAvailableException exception.
   */
  @Test
  void UsernameNotAvailableExceptionTest() {
    Assertions.assertThrows(UsernameNotAvailableException.class, () -> {
      throw new UsernameNotAvailableException("Test.");
    });
  }
}