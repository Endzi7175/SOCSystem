package com.team20.siemcenter.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Alarm entity.
 */
@ApiModel(description = "The Alarm entity, Represents an alarm raised in certain situations.")
public class AlarmDTO implements Serializable {

    private Long id;

    private String message;

    private Instant timestamp;

    private Boolean activated;

    private Integer repeatCount;

    private Instant firstTimestamp;

    private Boolean dismissed;

    private String context;


    private Long siemAgentId;

    private Long alarmDefinitionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean isActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    public Instant getFirstTimestamp() {
        return firstTimestamp;
    }

    public void setFirstTimestamp(Instant firstTimestamp) {
        this.firstTimestamp = firstTimestamp;
    }

    public Boolean isDismissed() {
        return dismissed;
    }

    public void setDismissed(Boolean dismissed) {
        this.dismissed = dismissed;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Long getSiemAgentId() {
        return siemAgentId;
    }

    public void setSiemAgentId(Long siemAgentId) {
        this.siemAgentId = siemAgentId;
    }

    public Long getAlarmDefinitionId() {
        return alarmDefinitionId;
    }

    public void setAlarmDefinitionId(Long alarmDefinitionId) {
        this.alarmDefinitionId = alarmDefinitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlarmDTO alarmDTO = (AlarmDTO) o;
        if (alarmDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alarmDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlarmDTO{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", activated='" + isActivated() + "'" +
            ", repeatCount=" + getRepeatCount() +
            ", firstTimestamp='" + getFirstTimestamp() + "'" +
            ", dismissed='" + isDismissed() + "'" +
            ", context='" + getContext() + "'" +
            ", siemAgent=" + getSiemAgentId() +
            ", alarmDefinition=" + getAlarmDefinitionId() +
            "}";
    }
}
