package org.alpha.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * Unit test for organization persistence.
 */
public class OrganizationPersistenceTest extends BasePersistenceUnitTest {

    /**
     * The logger to use for error logging.
     */
    private static final Logger LOGGER = LogManager.getLogger(OrganizationPersistenceTest.class);

    /**
     * Basic create/retrieve test.
     * @throws Exception if an error occurs
     */
    @Test
    public void test_create_retrieve() throws Exception {
        LOGGER.log(Level.WARN, "Running test.");
        // Save organization
        Organization org = new Organization();
        org.setName("test");
        org.setAccessToken("accessToken");
        persist(org);

        // Retrieve organization
        beginTransaction();
        Organization retrieved = getEntityManager().find(Organization.class, org.getId());
        assertNotNull("'createdAt' should not be null.", retrieved.getCreatedAt());
        assertNotNull("'updatedAt' should not be null.", retrieved.getUpdatedAt());
        assertEquals("'createdAt' and 'updatedAt' should be the same.", retrieved.getCreatedAt(),
                retrieved.getUpdatedAt());
        commitTransaction();

    }
}
