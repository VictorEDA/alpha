package org.alpha.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Unit test for organization persistence.
 */
public class OrganizationPersistenceTest extends BasePersistenceUnitTest {

    /**
     * Basic create/retrieve test.
     * @throws Exception if an error occurs
     */
    @Test
    public void test_create_retrieve() throws Exception {
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

    /**
     * Basic create/retrieve test with users.
     * @throws Exception if an error occurs
     */
    @Test
    public void test_create_retrieve_with_users() throws Exception {
        // Save organization
        Organization org = new Organization();
        org.setName("test2");
        org.setAccessToken("accessToken");
        persist(org);
        List<User> users = new ArrayList<>();
        users.add(createUser("user1"));
        users.add(createUser("user2"));
        org.setUsers(users);
        for (User user : users) {
            user.setOrganization(org);
            persist(user);
        }

        // Retrieve organization
        beginTransaction();
        Organization retrieved = getEntityManager().find(Organization.class, org.getId());
        assertNotNull("'createdAt' should not be null.", retrieved.getCreatedAt());
        assertNotNull("'updatedAt' should not be null.", retrieved.getUpdatedAt());
        assertEquals("'createdAt' and 'updatedAt' should be the same.", retrieved.getCreatedAt(),
                retrieved.getUpdatedAt());
        assertEquals("2 users should be present", 2, retrieved.getUsers().size());
        commitTransaction();

    }

    /**
     * Create a user.
     * @param customerUser The customer user id.
     * @return the created user
     */
    private User createUser(String customerUser) {
        User user = new User();
        user.setAccessToken("accessToken");
        user.setCustomerUserId(customerUser);
        return user;
    }

}
