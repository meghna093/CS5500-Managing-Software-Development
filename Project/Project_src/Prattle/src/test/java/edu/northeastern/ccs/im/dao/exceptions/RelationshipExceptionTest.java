package edu.northeastern.ccs.im.dao.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit test class for RelationshipExceptionTest.
 *
 * @author Akshat Shukla
 */
class RelationshipExceptionTest {
    /**
     * Test RelationshipExceptionTest exception.
     */
    @Test
    void RelationshipExceptionTest() {
        Assertions.assertThrows(RelationshipException.class, () -> {
            throw new RelationshipException("Test.");
        });
    }
}