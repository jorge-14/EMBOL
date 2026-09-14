package backofficeapi.domain.model;

import backofficeapi.domain.enums.UserStatus;
import backofficeapi.domain.model.base.Auditable;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 11/09/2026
 */
public class AuthUserModel extends Auditable {
    private Long id;
    private String entraId;
    private String username;
    private String name;
    private String fatherLastname;
    private String motherLastname;
    private UserStatus userStatus;

    public AuthUserModel() {

    }

    public AuthUserModel(Long id, String entraId, String username, String name,
                         String fatherLastname, String motherLastname, UserStatus userStatus) {
        this.id = id;
        this.entraId = entraId;
        this.username = username;
        this.name = name;
        this.fatherLastname = fatherLastname;
        this.motherLastname = motherLastname;
        this.userStatus = userStatus;
    }

    public AuthUserModel(String entraId, String username, String name,
                         String fatherLastname, String motherLastname) {
        this.entraId = entraId;
        this.username = username;
        this.name = name;
        this.fatherLastname = fatherLastname;
        this.motherLastname = motherLastname;
        this.userStatus = UserStatus.ACTIVE;
    }
    public void updateFromEntraId(String username, String name,
                                  String fatherLastname, String motherLastname) {
        this.username = username;
        this.name = name;
        this.fatherLastname = fatherLastname;
        this.motherLastname = motherLastname;
    }

    public void activate() {
        this.userStatus = UserStatus.ACTIVE;
    }

    public void deactivate() {
        this.userStatus = UserStatus.INACTIVE;
    }

    public void delete() {
        this.userStatus = UserStatus.DELETED;
        markAsDeleted();
    }

    public boolean isActive() {
        return this.userStatus == UserStatus.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public String getEntraId() {
        return entraId;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getFatherLastname() {
        return fatherLastname;
    }

    public String getMotherLastname() {
        return motherLastname;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEntraId(String entraId) {
        this.entraId = entraId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFatherLastname(String fatherLastname) {
        this.fatherLastname = fatherLastname;
    }

    public void setMotherLastname(String motherLastname) {
        this.motherLastname = motherLastname;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
