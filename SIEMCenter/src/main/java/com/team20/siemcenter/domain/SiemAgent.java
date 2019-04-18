package com.team20.siemcenter.domain;



import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * The SiemAgent entity,
 * Represents a simple application on MT CORP computers for acquiring their logs.
 */
@Entity
@Table(name = "siem_agent")
@Document(indexName = "siemagent")
public class SiemAgent implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cn")
    private String cn;

    @Column(name = "public_key")
    private String publicKey;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "active")
    private Boolean active;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCn() {
        return cn;
    }

    public SiemAgent cn(String cn) {
        this.cn = cn;
        return this;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public SiemAgent publicKey(String publicKey) {
        this.publicKey = publicKey;
        return this;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public SiemAgent ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Boolean isActive() {
        return active;
    }

    public SiemAgent active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        SiemAgent siemAgent = (SiemAgent) o;
        if (siemAgent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), siemAgent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SiemAgent{" +
            "id=" + getId() +
            ", cn='" + getCn() + "'" +
            ", publicKey='" + getPublicKey() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
