package org.alpha.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The user in an organization entity.
 */
@Entity
@Table(name = "organization_user")
public class User extends BaseEntity {

    /**
     * Generated UID.
     */
    private static final long serialVersionUID = 456415137880441734L;

    /**
     * The maximum size, in bytes, of customer user id.
     */
    public static final int CUSTOMER_USER_ID_MAX_SIZE = 255;

    /**
     * The organization name.
     */
    @NotNull
    @NotEmpty
    @Size(max = CUSTOMER_USER_ID_MAX_SIZE)
    @Column(unique = true)
    private String customerUserId;

    /**
     * The access token for the organization.
     * <p>
     * TODO: Encrypt access token in DB.
     */
    @NotNull
    @NotEmpty
    @Size(max = ACCESS_TOKEN_MAX_SIZE)
    @Pattern(regexp = "^[a-zA-Z0-9_]*$")
    private String accessToken;

    /**
     * The organization that this user belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    @JsonIgnore
    private Organization organization;

    /**
     * Default constructor.
     */
    public User() {
        // empty
    }

    /**
     * Retrieves the 'customerUserId' variable.
     * @return the 'customerUserId' variable value
     */
    public String getCustomerUserId() {
        return customerUserId;
    }

    /**
     * Sets the 'customerUserId' variable.
     * @param customerUserId the new 'customerUserId' variable value to set
     */
    public void setCustomerUserId(String customerUserId) {
        this.customerUserId = customerUserId;
    }

    /**
     * Retrieves the 'accessToken' variable.
     * @return the 'accessToken' variable value
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the 'accessToken' variable.
     * @param accessToken the new 'accessToken' variable value to set
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Retrieves the 'organization' variable.
     * @return the 'organization' variable value
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * Sets the 'organization' variable.
     * @param organization the new 'organization' variable value to set
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

}
