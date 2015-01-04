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
     * @throws AppServiceException if an error occurs, or EntityExistsException if organization already exists
     */
    void create(Organization organization) throws AppServiceException;

    /**
     * Retrieve organization.
     * @param organizationId The organization id.
     * @return the organization instance
     * @throws IllegalArgumentException if organizationId is zero or negative
     * @throws AppServiceException if an error occurs, or EntityNotFoundException if organization was not
     *             found
     */
    Organization read(long organizationId) throws AppServiceException;

    /**
     * Update organization.
     * @param organization The organization to update.
     * @throws IllegalArgumentException if organization is null
     * @throws AppServiceException if an error occurs
     */
    void update(Organization organization) throws AppServiceException;

    /**
     * Delete organization from persistence.
     * @param organizationId The organization id.
     * @throws IllegalArgumentException if organizationId zero or negative
     * @throws AppServiceException if an error occurs
     */
    void delete(long organizationId) throws AppServiceException;

}
