package io.fourfinanceit.featuros.notification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class CreateNotification {

    private String name;
    private String product;
    private String group;
    private String version;

    @JsonCreator
    public CreateNotification(@JsonProperty("name") String name,
                              @JsonProperty("product") String product,
                              @JsonProperty("group") String group,
                              @JsonProperty("version") String version) {
        this.name = name;
        this.product = product;
        this.group = group;
        this.version = version;
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
}