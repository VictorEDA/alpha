package org.alpha.entities;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.validation.ValidationException;

import org.alpha.BaseTest;
import org.alpha.TestUtility;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * The base class for persistence unit tests.
 */
public class BasePersistenceUnitTest extends BaseTest {

    /**
     * The persistence unit name to use for connecting to persistence.
     */
    static final String PERSISTENCE_UNIT = "persistenceTest";

    /**
     * Represents the <code>EntityManagerFactory </code> for tests.
     */
    private static EntityManagerFactory factory;

    /**
     * Represents the entity manager used in tests.
     */
    private EntityManager entityManager;



    /**
     * Sets up the unit tests.
     * @throws Exception to JUnit.
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
        // Set up Derby directories.
        Properties props = System.getProperties();
        props.setProperty("derby.system.home", "./target/derbyDB");
        factory = CustomPersistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        clearDb();
    }

    /**
     * Cleans up the unit tests.
     * @throws Exception to JUnit.
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
        factory.close();
        factory = null;
    }

    /**
     * Sets up the unit tests.
     * @throws Exception to JUnit.
     */
    @Before
    public void setUp() throws Exception {
        entityManager = factory.createEntityManager();
    }

    /**
     * Cleans up the unit tests.
     * @throws Exception to JUnit.
     */
    @After
    public void tearDown() throws Exception {
        clearDb();
        entityManager.close();
        entityManager = null;
    }

    /**
     * Begin transaction.
     * @throws Exception if an error occurs
     */
    protected void beginTransaction() {
        entityManager.clear();
        entityManager.getTransaction().begin();
    }

    /**
     * Commit transaction.
     * @throws Exception if an error occurs
     */
    protected void commitTransaction() {
        entityManager.getTransaction().commit();
        entityManager.clear();
    }

    /**
     * Persists the entity.
     * @param <T> the entity type.
     * @param entity the entity.
     * @throws Exception if an error occurs
     */
    protected <T> void persist(T entity) throws Exception {
        beginTransaction();

        try {
            entityManager.persist(entity);
            commitTransaction();
        } catch (PersistenceException | ValidationException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }

    }

    /**
     * Update the entity.
     * @param <T> the entity type.
     * @param entity the entity.
     * @return the updated entity
     */
    protected <T> T update(T entity) {
        beginTransaction();

        entity = entityManager.merge(entity);

        commitTransaction();
        return entity;
    }

    /**
     * Gets an entity manager.
     * @return the entity manager.
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Clears the database.
     * @throws Exception to JUnit.
     */
    private static void clearDb() throws Exception {
        TestUtility.executeSql(factory.createEntityManager(), TestUtility.TEST_FILES + "clear.sql");
    }

}

