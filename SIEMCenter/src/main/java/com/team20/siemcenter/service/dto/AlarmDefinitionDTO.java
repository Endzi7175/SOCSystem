package com.team20.siemcenter.service.dto;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import com.team20.siemcenter.domain.enumeration.AlarmRadius;
import com.team20.siemcenter.domain.enumeration.SiemLogSeverityEnum;
import com.team20.siemcenter.domain.enumeration.SiemLogSourceEnum;
import com.team20.siemcenter.domain.enumeration.OperatingSystem;

/**
 * A DTO for the AlarmDefinition entity.
 */
@ApiModel(description = "The AlarmDefinition entity, Represents an alarm definition.")
public class AlarmDefinitionDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private Integer numRepeats;

    private Long timeSpan;

    private String keyword;

    private String fieldname;

    private AlarmRadius alarmRadius;

    private SiemLogSeverityEnum severity;

    private SiemLogSourceEnum logSource;

    private Boolean active;

    private OperatingSystem operatingSystem;


    private Long siemLogTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumRepeats() {
        return numRepeats;
    }

    public void setNumRepeats(Integer numRepeats) {
        this.numRepeats = numRepeats;
    }

    public Long getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(Long timeSpan) {
        this.timeSpan = timeSpan;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public AlarmRadius getAlarmRadius() {
        return alarmRadius;
    }

    public void setAlarmRadius(AlarmRadius alarmRadius) {
        this.alarmRadius = alarmRadius;
    }

    public SiemLogSeverityEnum getSeverity() {
        return severity;
    }

    public void setSeverity(SiemLogSeverityEnum severity) {
        this.severity = severity;
    }

    public SiemLogSourceEnum getLogSource() {
        return logSource;
    }

    public void setLogSource(SiemLogSourceEnum logSource) {
        this.logSource = logSource;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public Long getSiemLogTypeId() {
        return siemLogTypeId;
    }

    public void setSiemLogTypeId(Long siemLogTypeId) {
        this.siemLogTypeId = siemLogTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlarmDefinitionDTO alarmDefinitionDTO = (AlarmDefinitionDTO) o;
        if (alarmDefinitionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alarmDefinitionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlarmDefinitionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", numRepeats=" + getNumRepeats() +
            ", timeSpan=" + getTimeSpan() +
            ", keyword='" + getKeyword() + "'" +
            ", fieldname='" + getFieldname() + "'" +
            ", alarmRadius='" + getAlarmRadius() + "'" +
            ", severity='" + getSeverity() + "'" +
            ", logSource='" + getLogSource() + "'" +
            ", active='" + isActive() + "'" +
            ", operatingSystem='" + getOperatingSystem() + "'" +
            ", siemLogType=" + getSiemLogTypeId() +
            "}";
    }
}
