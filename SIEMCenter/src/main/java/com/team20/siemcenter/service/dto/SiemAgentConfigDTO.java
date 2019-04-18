package com.team20.siemcenter.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SiemAgentConfig entity.
 */
@ApiModel(description = "The SiemAgentConfig entity, Represents siem agent's configuration file.")
public class SiemAgentConfigDTO implements Serializable {

    private Long id;

    private String logDestinationIp;


    private Long siemAgentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogDestinationIp() {
        return logDestinationIp;
    }

    public void setLogDestinationIp(String logDestinationIp) {
        this.logDestinationIp = logDestinationIp;
    }

    public Long getSiemAgentId() {
        return siemAgentId;
    }

    public void setSiemAgentId(Long siemAgentId) {
        this.siemAgentId = siemAgentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SiemAgentConfigDTO siemAgentConfigDTO = (SiemAgentConfigDTO) o;
        if (siemAgentConfigDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), siemAgentConfigDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SiemAgentConfigDTO{" +
            "id=" + getId() +
            ", logDestinationIp='" + getLogDestinationIp() + "'" +
            ", siemAgent=" + getSiemAgentId() +
            "}";
    }
}
