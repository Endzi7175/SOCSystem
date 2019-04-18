package com.team20.siemcenter.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import com.team20.siemcenter.domain.enumeration.OperatingSystem;

/**
 * A DTO for the SiemLogType entity.
 */
@ApiModel(description = "The SiemLogType entity, Represents a siem agent log type.")
public class SiemLogTypeDTO implements Serializable {

    private Long id;

    private String timestampRegex;

    private String severityRegex;

    private String contextRegex;

    private String messageRegex;

    private String name;

    private String dateTimeFormat;

    private OperatingSystem operatingSystem;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimestampRegex() {
        return timestampRegex;
    }

    public void setTimestampRegex(String timestampRegex) {
        this.timestampRegex = timestampRegex;
    }

    public String getSeverityRegex() {
        return severityRegex;
    }

    public void setSeverityRegex(String severityRegex) {
        this.severityRegex = severityRegex;
    }

    public String getContextRegex() {
        return contextRegex;
    }

    public void setContextRegex(String contextRegex) {
        this.contextRegex = contextRegex;
    }

    public String getMessageRegex() {
        return messageRegex;
    }

    public void setMessageRegex(String messageRegex) {
        this.messageRegex = messageRegex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SiemLogTypeDTO siemLogTypeDTO = (SiemLogTypeDTO) o;
        if (siemLogTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), siemLogTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SiemLogTypeDTO{" +
            "id=" + getId() +
            ", timestampRegex='" + getTimestampRegex() + "'" +
            ", severityRegex='" + getSeverityRegex() + "'" +
            ", contextRegex='" + getContextRegex() + "'" +
            ", messageRegex='" + getMessageRegex() + "'" +
            ", name='" + getName() + "'" +
            ", dateTimeFormat='" + getDateTimeFormat() + "'" +
            ", operatingSystem='" + getOperatingSystem() + "'" +
            "}";
    }
}
