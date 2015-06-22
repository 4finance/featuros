package io.fourfinanceit.featuros.notification;

import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.hateoas.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@JsonRootName("notification")
@Entity
class Notification implements Identifiable<Long>, INotification {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String product;

    @Column(name = "group_")
    private String group;

    private String version;

    private Instant date;

    private String address;

    /**
     * @deprecated persistence only
     */
    @Deprecated
    private Notification() {
    }

    public Notification(String name, String product, String group, String version, Instant date, String address) {
        this.name = name;
        this.product = product;
        this.group = group;
        this.version = version;
        this.date = date;
        this.address = address;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getProduct() {
        return product;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public Instant getDate() {
        return date;
    }

    @Override
    public String getAddress() {
        return address;
    }
}
