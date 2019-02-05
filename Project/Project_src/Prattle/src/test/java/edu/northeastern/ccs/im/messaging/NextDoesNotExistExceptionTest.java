package edu.northeastern.ccs.im.messaging;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Junit test class for NextDoesNotExistExceptionTest.
 *
 * @author Matthew Lazarcheck
 */
class NextDoesNotExistExceptionTest {
  /**
   * Test NextDoesNotExistException exception.
   */
  @Test
  void testNextDoesNotExistException() {
    Assertions.assertThrows(NextDoesNotExistException.class, () -> {
      throw new NextDoesNotExistException("Test.");
    });
  }
}