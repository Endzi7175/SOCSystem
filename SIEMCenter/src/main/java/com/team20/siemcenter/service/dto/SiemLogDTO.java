package com.team20.siemcenter.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import com.team20.siemcenter.domain.enumeration.OperatingSystem;
import com.team20.siemcenter.domain.enumeration.SiemLogSeverityEnum;
import com.team20.siemcenter.domain.enumeration.SiemLogSourceEnum;

/**
 * A DTO for the SiemLog entity.
 */
@ApiModel(description = "The SiemLog entity, Represents a log from siem agent.")
public class SiemLogDTO implements Serializable {

    private Long id;

    private OperatingSystem operatingSystem;

    private String operatingSystemVersion;

    private String internalIp;

    private String externalIp;

    private String hostName;

    private String context;

    private String message;

    private Instant timestamp;

    private String directory;

    private SiemLogSeverityEnum severity;

    private SiemLogSourceEnum source;

    private String filePath;

    private String rawMessage;


    private Long siemLogTypeId;

    private Long siemAgentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getOperatingSystemVersion() {
        return operatingSystemVersion;
    }

    public void setOperatingSystemVersion(String operatingSystemVersion) {
        this.operatingSystemVersion = operatingSystemVersion;
    }

    public String getInternalIp() {
        return internalIp;
    }

    public void setInternalIp(String internalIp) {
        this.internalIp = internalIp;
    }

    public String getExternalIp() {
        return externalIp;
    }

    public void setExternalIp(String externalIp) {
        this.externalIp = externalIp;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public SiemLogSeverityEnum getSeverity() {
        return severity;
    }

    public void setSeverity(SiemLogSeverityEnum severity) {
        this.severity = severity;
    }

    public SiemLogSourceEnum getSource() {
        return source;
    }

    public void setSource(SiemLogSourceEnum source) {
        this.source = source;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    public Long getSiemLogTypeId() {
        return siemLogTypeId;
    }

    public void setSiemLogTypeId(Long siemLogTypeId) {
        this.siemLogTypeId = siemLogTypeId;
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

        SiemLogDTO siemLogDTO = (SiemLogDTO) o;
        if (siemLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), siemLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SiemLogDTO{" +
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
            ", siemLogType=" + getSiemLogTypeId() +
            ", siemAgent=" + getSiemAgentId() +
            "}";
    }
}
