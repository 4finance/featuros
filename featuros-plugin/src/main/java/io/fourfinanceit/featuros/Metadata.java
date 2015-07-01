package io.fourfinanceit.featuros;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Stream;

class Metadata {

    private static final Logger log = LoggerFactory.getLogger(Metadata.class);

    private String name;
    private String product;
    private String group;
    private String version;

    private Metadata(String name, String product, String group, String version) {
        log.info("Loaded featuros metadata [name: {}, product: {}, group: {}, version: {}]", name, product, group, version);
        this.name = name;
        this.product = product;
        this.group = group;
        this.version = version;
    }

    static Optional<Metadata> load(String resourcePath) {
        try (InputStream is = Metadata.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                return Optional.empty();
            }
            Properties properties = new Properties();
            properties.load(is);

            Metadata loaded = new Metadata(
                    properties.getProperty("name"),
                    properties.getProperty("product"),
                    properties.getProperty("group"),
                    properties.getProperty("version")
            );

            return Optional.of(loaded).filter(m -> Stream.of(m.name, m.product, m.group, m.version).noneMatch(Objects::isNull));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    Map<String, Object> asMap() {
        Map<String, Object> valueMap = new HashMap<>();
        valueMap.put("name", name);
        valueMap.put("product", product);
        valueMap.put("group", group);
        valueMap.put("version", version);
        return valueMap;
    }

}