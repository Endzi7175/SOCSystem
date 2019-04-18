package com.team20.siemcenter.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * The Alarm entity,
 * Represents an alarm raised in certain situations.
 */
@Entity
@Table(name = "alarm")
@Document(indexName = "alarm")
public class Alarm implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "jhi_timestamp")
    private Instant timestamp;

    @Column(name = "activated")
    private Boolean activated;

    @Column(name = "repeat_count")
    private Integer repeatCount;

    @Column(name = "first_timestamp")
    private Instant firstTimestamp;

    @Column(name = "dismissed")
    private Boolean dismissed;

    @Column(name = "context")
    private String context;

    @ManyToOne
    @JsonIgnoreProperties("alarms")
    private SiemAgent siemAgent;

    @ManyToOne
    @JsonIgnoreProperties("alarms")
    private AlarmDefinition alarmDefinition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public Alarm message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Alarm timestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean isActivated() {
        return activated;
    }

    public Alarm activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public Alarm repeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
        return this;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    public Instant getFirstTimestamp() {
        return firstTimestamp;
    }

    public Alarm firstTimestamp(Instant firstTimestamp) {
        this.firstTimestamp = firstTimestamp;
        return this;
    }

    public void setFirstTimestamp(Instant firstTimestamp) {
        this.firstTimestamp = firstTimestamp;
    }

    public Boolean isDismissed() {
        return dismissed;
    }

    public Alarm dismissed(Boolean dismissed) {
        this.dismissed = dismissed;
        return this;
    }

    public void setDismissed(Boolean dismissed) {
        this.dismissed = dismissed;
    }

    public String getContext() {
        return context;
    }

    public Alarm context(String context) {
        this.context = context;
        return this;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public SiemAgent getSiemAgent() {
        return siemAgent;
    }

    public Alarm siemAgent(SiemAgent siemAgent) {
        this.siemAgent = siemAgent;
        return this;
    }

    public void setSiemAgent(SiemAgent siemAgent) {
        this.siemAgent = siemAgent;
    }

    public AlarmDefinition getAlarmDefinition() {
        return alarmDefinition;
    }

    public Alarm alarmDefinition(AlarmDefinition alarmDefinition) {
        this.alarmDefinition = alarmDefinition;
        return this;
    }

    public void setAlarmDefinition(AlarmDefinition alarmDefinition) {
        this.alarmDefinition = alarmDefinition;
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
        Alarm alarm = (Alarm) o;
        if (alarm.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alarm.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Alarm{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", timestamp='" + getTimestamp() + "'" +
            ", activated='" + isActivated() + "'" +
            ", repeatCount=" + getRepeatCount() +
            ", firstTimestamp='" + getFirstTimestamp() + "'" +
            ", dismissed='" + isDismissed() + "'" +
            ", context='" + getContext() + "'" +
            "}";
    }
}
