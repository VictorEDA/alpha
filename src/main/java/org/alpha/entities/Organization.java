package org.alpha.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * The organization entity.
 */
@Entity
@Table(name = "organization")
public class Organization extends BaseEntity {

    /**
     * Generated UID.
     */
    private static final long serialVersionUID = -4005774168638718756L;

    /**
     * The maximum size, in bytes, of text variable module.
     */
    public static final int ORGANIZATION_NAME_MAX_SIZE = 255;

    /**
     * The organization name.
     */
    @NotNull
    @NotEmpty
    @Size(max = ORGANIZATION_NAME_MAX_SIZE)
    @Column(unique = true)
    private String name;

    /**
     * The access token for the organization.
     */
    @NotNull
    @NotEmpty
    @Size(max = ACCESS_TOKEN_MAX_SIZE)
    @Pattern(regexp = "^[a-zA-Z0-9_]*$")
    private String accessToken;

    /**
     * Constructor.
     */
    public Organization() {
        // empty
    }

    /**
     * Retrieves the 'name' variable.
     * @return the 'name' variable value
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the 'name' variable.
     * @param name the new 'name' variable value to set
     */
    public void setName(String name) {
        this.name = name;
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

}
