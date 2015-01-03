package org.alpha.services;

import org.alpha.entities.Organization;

/**
 * The CRUD interface for organization entity.
 */
public interface OrganizationService {

    /**
     * Create organization.
     * @param organization The organization to create.
     * @throws IllegalArgumentException if organization is null
     * @throws AppServiceException if an error occurs
     */
    void create(Organization organization) throws AppServiceException;

    /**
     * Retrieve organization.
     * @param organizationId The organization id.
     * @return the organization instance
     * @throws IllegalArgumentException if organizationId is null/empty
     * @throws AppServiceException if an error occurs, or EntityNotFoundException if user was not found
     */
    Organization read(String organizationId) throws AppServiceException;

    /**
     * Update organization.
     * @param organization The organization to update.
     * @throws IllegalArgumentException if user is null
     * @throws AppServiceException if an error occurs
     */
    void update(Organization organization) throws AppServiceException;

    /**
     * Delete organization from persistence.
     * @param organizationId The organization id.
     * @throws IllegalArgumentException if organizationId is null/empty
     * @throws AppServiceException if an error occurs
     */
    void delete(String organizationId) throws AppServiceException;

}
