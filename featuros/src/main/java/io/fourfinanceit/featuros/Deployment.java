package io.fourfinanceit.featuros;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Deployment {

    private String id;

    private String name;

    private String group;

    private String version;

    private Instant date;

    @JsonCreator
    public Deployment(@JsonProperty("id") String id,
                      @JsonProperty("name") String name,
                      @JsonProperty("group") String group,
                      @JsonProperty("version") String version) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.version = version;
        this.date = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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
