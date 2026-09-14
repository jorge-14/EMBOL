package backofficeapi.domain.model.base;

import java.time.LocalDateTime;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 11/09/2026
 */
public abstract class Auditable {
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String createdBy;
    private String modifiedBy;
    private boolean deleted;

    protected Auditable() {
        this.deleted = false;
    }

    public void markAsDeleted() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
