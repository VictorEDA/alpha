package org.alpha.services.jpa;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.alpha.Helper;
import org.alpha.entities.Organization;
import org.alpha.entities.User;
import org.alpha.services.AppServiceException;
import org.alpha.services.EntityExistsException;
import org.alpha.services.EntityNotFoundException;
import org.alpha.services.OrganizationService;
import org.apache.logging.log4j.LogManager;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of organization service.
 */
public class OrganizationServiceJpa extends BaseServiceJpa implements OrganizationService {

    /**
     * JPQL query to get organization by name.
     */
    static final String GET_ORGANIZATION_BY_NAME = "SELECT b FROM Organization b WHERE b.name = :name";

    /**
     * JPQL query to get user by customer user id.
     */
    static final String GET_USER_BY_CUSTOMER_USER_ID =
            "SELECT b FROM User b WHERE b.customerUserId = :customerUserId";

    /**
     * Constructor. Initializes logger.
     */
    public OrganizationServiceJpa() {
        setLogger(LogManager.getLogger(this.getClass()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Organization organization) throws AppServiceException {
        final String methodName = "create";

        // Log method entrance
        Helper.logEntrance(getLogger(), methodName, "organization", organization);

        // Check input
        Helper.checkNull(getLogger(), methodName, organization, "organization");

        try {
            // Check if organization already exists.
            if (findOrganization(organization.getName()) == null) {
                getEntityManager().persist(organization);
            } else {
                throw Helper.logException(getLogger(), methodName, new EntityExistsException(
                        "Organization with name '" + organization.getName() + "' already exists."));
            }
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            Helper.handleJpaException(getLogger(), methodName, e);
        }

        // Log method exit
        Helper.logExit(getLogger(), methodName);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(User user) throws AppServiceException {
        final String methodName = "create";

        // Log method entrance
        Helper.logEntrance(getLogger(), methodName, "user", user);

        // Check input
        Helper.checkNull(getLogger(), methodName, user, "user");

        try {
            // Check if user already exists.
            if (findUser(user.getCustomerUserId()) == null) {
                getEntityManager().persist(user);
            } else {
                throw Helper.logException(getLogger(), methodName, new EntityExistsException(
                        "User with customer user id '" + user.getCustomerUserId() + "' already exists."));
            }
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            Helper.handleJpaException(getLogger(), methodName, e);
        }

        // Log method exit
        Helper.logExit(getLogger(), methodName);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Organization read(long organizationId, boolean fetchUsers) throws AppServiceException {
        final String methodName = "read";
        Helper.logEntrance(getLogger(), methodName, "organizationId", organizationId);

        Helper.checkZeroOrNegative(getLogger(), methodName, organizationId, "organizationId");

        Organization result = null;

        try {
            result = getEntityManager().find(Organization.class, organizationId);
            if (result == null) {
                throw Helper.logException(getLogger(), methodName, new EntityNotFoundException(
                        "Organization with name '" + organizationId + "' was not found."));
            }
            if (fetchUsers) {
                result.getUsers().size();
            }
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            Helper.handleJpaException(getLogger(), methodName, e);
        }

        return Helper.logExit(getLogger(), methodName, result);
    }

    /**
     * Find organization by name.
     * @param name The organization name.
     * @return the organization, or null if organization was not found
     */
    private Organization findOrganization(String name) {
        TypedQuery<Organization> query =
                getEntityManager().createQuery(GET_ORGANIZATION_BY_NAME, Organization.class);
        query.setParameter("name", name);
        List<Organization> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    /**
     * Find user by customer user id.
     * @param name The organization name.
     * @return the organization, or null if organization was not found
     */
    private User findUser(String customerUserId) {
        TypedQuery<User> query = getEntityManager().createQuery(GET_USER_BY_CUSTOMER_USER_ID, User.class);
        query.setParameter("customerUserId", customerUserId);
        List<User> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public void update(Organization organization) throws AppServiceException {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(long organizationId) throws AppServiceException {
        // TODO Auto-generated method stub

    }

}
