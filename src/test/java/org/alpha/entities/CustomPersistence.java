package org.alpha.entities;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolverHolder;

import org.hibernate.ejb.HibernatePersistence;

/**
 * Workaround for minor Hibernate bug https://hibernate.atlassian.net/browse/HHH-9141
 */
@SuppressWarnings({"deprecation", "rawtypes"})
public class CustomPersistence extends Persistence {

    public static EntityManagerFactory createEntityManagerFactory(String persistenceUnitName) {
        return CustomPersistence.createEntityManagerFactory(persistenceUnitName, null);
    }

    /**
     * Create the EntityManagerFactory instance.
     * @param persistenceUnitName The persistence unit.
     * @param properties Any additional EntityManagerFactory properties.
     * @return the EntityManagerFactory instance
     */
    public static EntityManagerFactory createEntityManagerFactory(String persistenceUnitName, Map properties) {
        EntityManagerFactory emf = null;
        List<PersistenceProvider> providers = getProviders();
        PersistenceProvider defaultProvider = null;
        for (PersistenceProvider provider : providers) {
            if (provider instanceof HibernatePersistence) {
                defaultProvider = provider;
                continue;
            }
            emf = provider.createEntityManagerFactory(persistenceUnitName, properties);
            if (emf != null) {
                break;
            }
        }
        if (emf == null && defaultProvider != null) {
            emf = defaultProvider.createEntityManagerFactory(persistenceUnitName, properties);
        }
        if (emf == null) {
            throw new PersistenceException("No Persistence provider for EntityManager named "
                    + persistenceUnitName);
        }
        return emf;
    }

    protected static List<PersistenceProvider> getProviders() {
        return PersistenceProviderResolverHolder.getPersistenceProviderResolver().getPersistenceProviders();
    }

}
