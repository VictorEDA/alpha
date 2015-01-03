package org.alpha.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

import org.alpha.Helper;

/**
 * The base entity for all JPA classes.
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    /**
     * Generated UID needed for Serializable implementation.
     */
    private static final long serialVersionUID = -8854612366680978064L;

    /**
     * The maximum size, in bytes, of access token.
     */
    public static final int ACCESS_TOKEN_MAX_SIZE = 255;

    /**
     * The unique item id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 0)
    @Column(name = "id")
    private long id;

    /**
     * The date of entity creation. The timestamp should be automatically set by persistence to NOW when
     * entity is created.
     */
    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    /**
     * The date when entity was updated. The timestamp should be automatically updated by persistence.
     */
    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    /**
     * Default constructor.
     */
    protected BaseEntity() {
        // empty
    }

    /**
     * Set the timestamps on create.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = createdAt;
    }

    /**
     * Set the update timestamp on update.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    /**
     * Retrieves the 'id' variable.
     * @return the 'id' variable value
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the 'id' variable.
     * @param id the new 'id' variable value to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retrieves the 'createdAt' variable.
     * @return the 'createdAt' variable value
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the 'createdAt' variable.
     * @param createdAt the new 'createdAt' variable value to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Retrieves the 'updatedAt' variable.
     * @return the 'updatedAt' variable value
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets the 'updatedAt' variable.
     * @param updatedAt the new 'updatedAt' variable value to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Override the default toString method for logging purposes.
     * @return JSON string
     */
    @Override
    public String toString() {
        return Helper.toJsonString(this);
    }

}
