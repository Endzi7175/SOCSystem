package com.team20.siemcenter.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The ObservedFolder entity,
 * Represents a logs container folder listed for observation in siem agent's config file.
 */
@Entity
@Table(name = "observed_folder")
@Document(indexName = "observedfolder")
public class ObservedFolder implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path")
    private String path;

    @Column(name = "description")
    private String description;

    @Column(name = "filter_regex")
    private String filterRegex;

    @ManyToOne
    @JsonIgnoreProperties("observedfolders")
    private SiemAgentConfig siemAgentConfig;

    @ManyToMany
    @JoinTable(name = "observed_folder_log_types",
               joinColumns = @JoinColumn(name = "observed_folder_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "log_types_id", referencedColumnName = "id"))
    private Set<SiemLogType> logTypes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public ObservedFolder path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public ObservedFolder description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilterRegex() {
        return filterRegex;
    }

    public ObservedFolder filterRegex(String filterRegex) {
        this.filterRegex = filterRegex;
        return this;
    }

    public void setFilterRegex(String filterRegex) {
        this.filterRegex = filterRegex;
    }

    public SiemAgentConfig getSiemAgentConfig() {
        return siemAgentConfig;
    }

    public ObservedFolder siemAgentConfig(SiemAgentConfig siemAgentConfig) {
        this.siemAgentConfig = siemAgentConfig;
        return this;
    }

    public void setSiemAgentConfig(SiemAgentConfig siemAgentConfig) {
        this.siemAgentConfig = siemAgentConfig;
    }

    public Set<SiemLogType> getLogTypes() {
        return logTypes;
    }

    public ObservedFolder logTypes(Set<SiemLogType> siemLogTypes) {
        this.logTypes = siemLogTypes;
        return this;
    }

    public void setLogTypes(Set<SiemLogType> siemLogTypes) {
        this.logTypes = siemLogTypes;
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
        ObservedFolder observedFolder = (ObservedFolder) o;
        if (observedFolder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), observedFolder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ObservedFolder{" +
            "id=" + getId() +
            ", path='" + getPath() + "'" +
            ", description='" + getDescription() + "'" +
            ", filterRegex='" + getFilterRegex() + "'" +
            "}";
    }
}
