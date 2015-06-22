package io.fourfinanceit.featuros.deployment;

import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.hateoas.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@JsonRootName("deployment")
@Entity
class Deployment implements Identifiable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String product;

    @Column(name = "group_")
    private String group;

    private String version;

    private Instant date;

    /**
     * @deprecated persistence only
     */
    @Deprecated
    private Deployment() {
    }

    public Deployment(String name, String product, String group, String version, Instant date) {
        this.name = name;
        this.product = product;
        this.group = group;
        this.version = version;
        this.date = date;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProduct() {
        return product;
    }

    public String getGroup() {
        return group;
    }

    public String getVersion() {
        return version;
    }

    public Instant getDate() {
        return date;
    }
}
