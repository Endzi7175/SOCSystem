package com.team20.siemcenter.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The SiemAgentConfig entity,
 * Represents siem agent's configuration file.
 */
@Entity
@Table(name = "siem_agent_config")
@Document(indexName = "siemagentconfig")
public class SiemAgentConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "log_destination_ip")
    private String logDestinationIp;

    @OneToMany(mappedBy = "siemAgentConfig")
    private Set<ObservedFolder> observedfolders = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("siemAgentConfigs")
    private SiemAgent siemAgent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogDestinationIp() {
        return logDestinationIp;
    }

    public SiemAgentConfig logDestinationIp(String logDestinationIp) {
        this.logDestinationIp = logDestinationIp;
        return this;
    }

    public void setLogDestinationIp(String logDestinationIp) {
        this.logDestinationIp = logDestinationIp;
    }

    public Set<ObservedFolder> getObservedfolders() {
        return observedfolders;
    }

    public SiemAgentConfig observedfolders(Set<ObservedFolder> observedFolders) {
        this.observedfolders = observedFolders;
        return this;
    }

    public SiemAgentConfig addObservedfolders(ObservedFolder observedFolder) {
        this.observedfolders.add(observedFolder);
        observedFolder.setSiemAgentConfig(this);
        return this;
    }

    public SiemAgentConfig removeObservedfolders(ObservedFolder observedFolder) {
        this.observedfolders.remove(observedFolder);
        observedFolder.setSiemAgentConfig(null);
        return this;
    }

    public void setObservedfolders(Set<ObservedFolder> observedFolders) {
        this.observedfolders = observedFolders;
    }

    public SiemAgent getSiemAgent() {
        return siemAgent;
    }

    public SiemAgentConfig siemAgent(SiemAgent siemAgent) {
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
        SiemAgentConfig siemAgentConfig = (SiemAgentConfig) o;
        if (siemAgentConfig.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), siemAgentConfig.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SiemAgentConfig{" +
            "id=" + getId() +
            ", logDestinationIp='" + getLogDestinationIp() + "'" +
            "}";
    }
}
