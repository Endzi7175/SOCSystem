package com.team20.siemcenter.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.team20.siemcenter.domain.enumeration.AlarmRadius;

import com.team20.siemcenter.domain.enumeration.SiemLogSeverityEnum;

import com.team20.siemcenter.domain.enumeration.SiemLogSourceEnum;

import com.team20.siemcenter.domain.enumeration.OperatingSystem;

/**
 * The AlarmDefinition entity,
 * Represents an alarm definition.
 */
@Entity
@Table(name = "alarm_definition")
@Document(indexName = "alarmdefinition")
public class AlarmDefinition implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "num_repeats")
    private Integer numRepeats;

    @Column(name = "time_span")
    private Long timeSpan;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "fieldname")
    private String fieldname;

    @Enumerated(EnumType.STRING)
    @Column(name = "alarm_radius")
    private AlarmRadius alarmRadius;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity")
    private SiemLogSeverityEnum severity;

    @Enumerated(EnumType.STRING)
    @Column(name = "log_source")
    private SiemLogSourceEnum logSource;

    @Column(name = "active")
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "operating_system")
    private OperatingSystem operatingSystem;

    @ManyToOne
    @JsonIgnoreProperties("alarmDefinitions")
    private SiemLogType siemLogType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AlarmDefinition name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public AlarmDefinition description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumRepeats() {
        return numRepeats;
    }

    public AlarmDefinition numRepeats(Integer numRepeats) {
        this.numRepeats = numRepeats;
        return this;
    }

    public void setNumRepeats(Integer numRepeats) {
        this.numRepeats = numRepeats;
    }

    public Long getTimeSpan() {
        return timeSpan;
    }

    public AlarmDefinition timeSpan(Long timeSpan) {
        this.timeSpan = timeSpan;
        return this;
    }

    public void setTimeSpan(Long timeSpan) {
        this.timeSpan = timeSpan;
    }

    public String getKeyword() {
        return keyword;
    }

    public AlarmDefinition keyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getFieldname() {
        return fieldname;
    }

    public AlarmDefinition fieldname(String fieldname) {
        this.fieldname = fieldname;
        return this;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public AlarmRadius getAlarmRadius() {
        return alarmRadius;
    }

    public AlarmDefinition alarmRadius(AlarmRadius alarmRadius) {
        this.alarmRadius = alarmRadius;
        return this;
    }

    public void setAlarmRadius(AlarmRadius alarmRadius) {
        this.alarmRadius = alarmRadius;
    }

    public SiemLogSeverityEnum getSeverity() {
        return severity;
    }

    public AlarmDefinition severity(SiemLogSeverityEnum severity) {
        this.severity = severity;
        return this;
    }

    public void setSeverity(SiemLogSeverityEnum severity) {
        this.severity = severity;
    }

    public SiemLogSourceEnum getLogSource() {
        return logSource;
    }

    public AlarmDefinition logSource(SiemLogSourceEnum logSource) {
        this.logSource = logSource;
        return this;
    }

    public void setLogSource(SiemLogSourceEnum logSource) {
        this.logSource = logSource;
    }

    public Boolean isActive() {
        return active;
    }

    public AlarmDefinition active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public AlarmDefinition operatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public SiemLogType getSiemLogType() {
        return siemLogType;
    }

    public AlarmDefinition siemLogType(SiemLogType siemLogType) {
        this.siemLogType = siemLogType;
        return this;
    }

    public void setSiemLogType(SiemLogType siemLogType) {
        this.siemLogType = siemLogType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AlarmDefinition alarmDefinition = (AlarmDefinition) o;
        if (alarmDefinition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alarmDefinition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlarmDefinition{" +
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
            "}";
    }
}
