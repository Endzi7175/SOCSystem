package com.team20.siemcenter.domain;



import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import com.team20.siemcenter.domain.enumeration.OperatingSystem;

/**
 * The SiemLogType entity,
 * Represents a siem agent log type.
 */
@Entity
@Table(name = "siem_log_type")
@Document(indexName = "siemlogtype")
public class SiemLogType implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp_regex")
    private String timestampRegex;

    @Column(name = "severity_regex")
    private String severityRegex;

    @Column(name = "context_regex")
    private String contextRegex;

    @Column(name = "message_regex")
    private String messageRegex;

    @Column(name = "name")
    private String name;

    @Column(name = "date_time_format")
    private String dateTimeFormat;

    @Enumerated(EnumType.STRING)
    @Column(name = "operating_system")
    private OperatingSystem operatingSystem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimestampRegex() {
        return timestampRegex;
    }

    public SiemLogType timestampRegex(String timestampRegex) {
        this.timestampRegex = timestampRegex;
        return this;
    }

    public void setTimestampRegex(String timestampRegex) {
        this.timestampRegex = timestampRegex;
    }

    public String getSeverityRegex() {
        return severityRegex;
    }

    public SiemLogType severityRegex(String severityRegex) {
        this.severityRegex = severityRegex;
        return this;
    }

    public void setSeverityRegex(String severityRegex) {
        this.severityRegex = severityRegex;
    }

    public String getContextRegex() {
        return contextRegex;
    }

    public SiemLogType contextRegex(String contextRegex) {
        this.contextRegex = contextRegex;
        return this;
    }

    public void setContextRegex(String contextRegex) {
        this.contextRegex = contextRegex;
    }

    public String getMessageRegex() {
        return messageRegex;
    }

    public SiemLogType messageRegex(String messageRegex) {
        this.messageRegex = messageRegex;
        return this;
    }

    public void setMessageRegex(String messageRegex) {
        this.messageRegex = messageRegex;
    }

    public String getName() {
        return name;
    }

    public SiemLogType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public SiemLogType dateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
        return this;
    }

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public SiemLogType operatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
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
        SiemLogType siemLogType = (SiemLogType) o;
        if (siemLogType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), siemLogType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SiemLogType{" +
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
