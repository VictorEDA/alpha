package org.alpha.services.jpa;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.alpha.AppConfigurationException;
import org.alpha.Helper;
import org.apache.logging.log4j.Logger;

/**
 * Base class for JPA persistence services.
 */
public abstract class BaseServiceJPA {

    /**
     * The logger to use for logging.
     */
    private Logger logger;

    /**
     * The entity manager used to connect to persistence. Can be injected by J2EE container.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Default constructor.
     */
    protected BaseServiceJPA() {
        // empty
    }

    /**
     * Check initialization.
     * @throws AppConfigurationException if logger is null or entityManager is null.
     */
    @PostConstruct
    public void checkInit() {
        Helper.checkNullConfig(logger, "logger");
        Helper.checkNullConfig(entityManager, "entityManager");
    }

    /**
     * Retrieves the 'logger' variable.
     * @return the 'logger' variable value
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Sets the 'logger' variable.
     * @param logger the new 'logger' variable value to set
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Retrieves the 'entityManager' variable.
     * @return the 'entityManager' variable value
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Sets the 'entityManager' variable.
     * @param entityManager the new 'entityManager' variable value to set
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
