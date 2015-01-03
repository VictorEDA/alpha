package org.alpha.services.jpa;

import javax.persistence.PersistenceException;

import org.alpha.Helper;
import org.alpha.entities.Organization;
import org.alpha.services.AppServiceException;
import org.alpha.services.OrganizationService;
import org.apache.logging.log4j.LogManager;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of organization service.
 */
public class OrganizationServiceJPA extends BaseServiceJPA implements OrganizationService {

    /**
     * Constructor. Initializes logger.
     */
    public OrganizationServiceJPA() {
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
            getEntityManager().persist(organization);
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            Helper.handleJpaException(getLogger(), methodName, e);
        }

        // Log method exit
        Helper.logExit(getLogger(), methodName);
        // TODO Auto-generated method stub

    }

    /**
     * X.
     * @param organizationId
     * @return
     * @throws AppServiceException
     */
    @Override
    public Organization read(String organizationId) throws AppServiceException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * X.
     * @param organization
     * @throws AppServiceException
     */
    @Override
    public void update(Organization organization) throws AppServiceException {
        // TODO Auto-generated method stub

    }

    /**
     * X.
     * @param organizationId
     * @throws AppServiceException
     */
    @Override
    public void delete(String organizationId) throws AppServiceException {
        // TODO Auto-generated method stub

    }

}
