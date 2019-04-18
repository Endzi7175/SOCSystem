package com.team20.siemcenter.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ObservedFolder entity.
 */
@ApiModel(description = "The ObservedFolder entity, Represents a logs container folder listed for observation in siem agent's config file.")
public class ObservedFolderDTO implements Serializable {

    private Long id;

    private String path;

    private String description;

    private String filterRegex;


    private Long siemAgentConfigId;

    private Set<SiemLogTypeDTO> logTypes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilterRegex() {
        return filterRegex;
    }

    public void setFilterRegex(String filterRegex) {
        this.filterRegex = filterRegex;
    }

    public Long getSiemAgentConfigId() {
        return siemAgentConfigId;
    }

    public void setSiemAgentConfigId(Long siemAgentConfigId) {
        this.siemAgentConfigId = siemAgentConfigId;
    }

    public Set<SiemLogTypeDTO> getLogTypes() {
        return logTypes;
    }

    public void setLogTypes(Set<SiemLogTypeDTO> siemLogTypes) {
        this.logTypes = siemLogTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObservedFolderDTO observedFolderDTO = (ObservedFolderDTO) o;
        if (observedFolderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), observedFolderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ObservedFolderDTO{" +
            "id=" + getId() +
            ", path='" + getPath() + "'" +
            ", description='" + getDescription() + "'" +
            ", filterRegex='" + getFilterRegex() + "'" +
            ", siemAgentConfig=" + getSiemAgentConfigId() +
            "}";
    }
}
