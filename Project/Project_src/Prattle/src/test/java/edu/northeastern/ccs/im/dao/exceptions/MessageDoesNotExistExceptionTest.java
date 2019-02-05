package edu.northeastern.ccs.im.dao.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Junit test class for MessageDoesNotExistException.
 *
 * @author Akshat Shukla
 */
class MessageDoesNotExistExceptionTest {
    /**
     * Test MessageDoesNotExistException exception.
     */
    @Test
    void MessageDoesNotExistExceptionTest() {
        Assertions.assertThrows(MessageDoesNotExistException.class, () -> {
            throw new MessageDoesNotExistException("Test.");
        });
    }
}