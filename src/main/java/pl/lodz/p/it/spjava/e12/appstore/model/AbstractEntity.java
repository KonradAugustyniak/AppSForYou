package pl.lodz.p.it.spjava.e12.appstore.model;

import java.util.Date;
import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractEntity {

    protected static final long serialVersionUID = 1L;

    protected abstract Object getId();

    protected abstract Object getBusinessKey();

    @Version
    @Column(nullable = false)
    private long version;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIMESTAMP")
    private Date creationTimestamp;

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MODIFY_TIMESTAMP")
    private Date modificationTimestamp;

    public Date getModificationTimestamp() {
        return modificationTimestamp;
    }

    @PreUpdate
    private void updateTimestamp() {
        modificationTimestamp = new Date();
    }

    @PrePersist
    private void creationTimestamp() {
        creationTimestamp = new Date();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[id= " + getId() + ", key= " + getBusinessKey() + ", version= " + version + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }

        if (this.getClass().isAssignableFrom(obj.getClass())) {
            return this.getBusinessKey().equals(((AbstractEntity) obj).getBusinessKey());
        } else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        return getBusinessKey().hashCode();
    }

}
