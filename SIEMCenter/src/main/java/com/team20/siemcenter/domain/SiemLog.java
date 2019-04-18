package com.team20.siemcenter.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.team20.siemcenter.domain.enumeration.OperatingSystem;

import com.team20.siemcenter.domain.enumeration.SiemLogSeverityEnum;

import com.team20.siemcenter.domain.enumeration.SiemLogSourceEnum;

/**
 * The SiemLog entity,
 * Represents a log from siem agent.
 */
@Entity
@Table(name = "siem_log")
@Document(indexName = "siemlog")
public class SiemLog implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "operating_system")
    private OperatingSystem operatingSystem;

    @Column(name = "operating_system_version")
    private String operatingSystemVersion;

    @Column(name = "internal_ip")
    private String internalIp;

    @Column(name = "external_ip")
    private String externalIp;

    @Column(name = "host_name")
    private String hostName;

    @Column(name = "context")
    private String context;

    @Column(name = "message")
    private String message;

    @Column(name = "jhi_timestamp")
    private Instant timestamp;

    @Column(name = "jhi_directory")
    private String directory;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity")
    private SiemLogSeverityEnum severity;

    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    private SiemLogSourceEnum source;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "raw_message")
    private String rawMessage;

    @ManyToOne
    @JsonIgnoreProperties("siemLogs")
    private SiemLogType siemLogType;

    @ManyToOne
    @JsonIgnoreProperties("siemLogs")
    private SiemAgent siemAgent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public SiemLog operatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getOperatingSystemVersion() {
        return operatingSystemVersion;
    }

    public SiemLog operatingSystemVersion(String operatingSystemVersion) {
        this.operatingSystemVersion = operatingSystemVersion;
        return this;
    }

    public void setOperatingSystemVersion(String operatingSystemVersion) {
        this.operatingSystemVersion = operatingSystemVersion;
    }

    public String getInternalIp() {
        return internalIp;
    }

    public SiemLog internalIp(String internalIp) {
        this.internalIp = internalIp;
        return this;
    }

    public void setInternalIp(String internalIp) {
        this.internalIp = internalIp;
    }

    public String getExternalIp() {
        return externalIp;
    }

    public SiemLog externalIp(String externalIp) {
        this.externalIp = externalIp;
        return this;
    }

    public void setExternalIp(String externalIp) {
        this.externalIp = externalIp;
    }

    public String getHostName() {
        return hostName;
    }

    public SiemLog hostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getContext() {
        return context;
    }

    public SiemLog context(String context) {
        this.context = context;
        return this;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getMessage() {
        return message;
    }

    public SiemLog message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public SiemLog timestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getDirectory() {
        return directory;
    }

    public SiemLog directory(String directory) {
        this.directory = directory;
        return this;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public SiemLogSeverityEnum getSeverity() {
        return severity;
    }

    public SiemLog severity(SiemLogSeverityEnum severity) {
        this.severity = severity;
        return this;
    }

    public void setSeverity(SiemLogSeverityEnum severity) {
        this.severity = severity;
    }

    public SiemLogSourceEnum getSource() {
        return source;
    }

    public SiemLog source(SiemLogSourceEnum source) {
        this.source = source;
        return this;
    }

    public void setSource(SiemLogSourceEnum source) {
        this.source = source;
    }

    public String getFilePath() {
        return filePath;
    }

    public SiemLog filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public SiemLog rawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
        return this;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    public SiemLogType getSiemLogType() {
        return siemLogType;
    }

    public SiemLog siemLogType(SiemLogType siemLogType) {
        this.siemLogType = siemLogType;
        return this;
    }

    public void setSiemLogType(SiemLogType siemLogType) {
        this.siemLogType = siemLogType;
    }

    public SiemAgent getSiemAgent() {
        return siemAgent;
    }

    public SiemLog siemAgent(SiemAgent siemAgent) {
        this.siemAgent = siemAgent;
        return this;
    }

    public void setSiemAgent(SiemAgent siemAgent) {
        this.siemAgent = siemAgent;
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
        SiemLog siemLog = (SiemLog) o;
        if (siemLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), siemLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SiemLog{" +
            "id=" + getId() +
            ", operatingSystem='" + getOperatingSystem() + "'" +
            ", operatingSystemVersion='" + getOperatingSystemVersion() + "'" +
            ", internalIp='" + getInternalIp() + "'" +
            ", externalIp='" + getExternalIp() + "'" +
            ", hostName='" + getHostName() + "'" +
            ", context='" + getContext() + "'" +
            ", message='" + getMessage() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", directory='" + getDirectory() + "'" +
            ", severity='" + getSeverity() + "'" +
            ", source='" + getSource() + "'" +
            ", filePath='" + getFilePath() + "'" +
            ", rawMessage='" + getRawMessage() + "'" +
            "}";
    }
}
