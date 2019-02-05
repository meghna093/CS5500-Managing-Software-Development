package edu.northeastern.ccs.im.dao.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Junit test class for GroupNameNotAvailableException.
 *
 * @author Matthew Lazarcheck
 */
class GroupNameNotAvailableExceptionTest {
  /**
   * Test GroupNameNotAvailableException exception.
   */
  @Test
  void GroupNameNotAvailableExceptionTest() {
    Assertions.assertThrows(GroupNameNotAvailableException.class, () -> {
      throw new GroupNameNotAvailableException("Test.");
    });
  }
}