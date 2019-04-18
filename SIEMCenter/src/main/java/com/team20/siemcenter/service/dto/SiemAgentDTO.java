package com.team20.siemcenter.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SiemAgent entity.
 */
@ApiModel(description = "The SiemAgent entity, Represents a simple application on MT CORP computers for acquiring their logs.")
public class SiemAgentDTO implements Serializable {

    private Long id;

    private String cn;

    private String publicKey;

    private String ipAddress;

    private Boolean active;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SiemAgentDTO siemAgentDTO = (SiemAgentDTO) o;
        if (siemAgentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), siemAgentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SiemAgentDTO{" +
            "id=" + getId() +
            ", cn='" + getCn() + "'" +
            ", publicKey='" + getPublicKey() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
