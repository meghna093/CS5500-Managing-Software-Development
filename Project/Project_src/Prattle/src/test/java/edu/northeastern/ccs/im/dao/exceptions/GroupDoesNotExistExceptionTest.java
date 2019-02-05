package edu.northeastern.ccs.im.dao.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Junit test class for GroupDoesNotExistException.
 *
 * @author Matthew Lazarcheck
 */
class GroupDoesNotExistExceptionTest {
  /**
   * Test GroupDoesNotExistException exception.
   */
  @Test
  void GroupDoesNotExistExceptionTest() {
    Assertions.assertThrows(GroupDoesNotExistException.class, () -> {
      throw new GroupDoesNotExistException("Test.");
    });
  }
}