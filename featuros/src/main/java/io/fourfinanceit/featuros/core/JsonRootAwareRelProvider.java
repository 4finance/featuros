package io.fourfinanceit.featuros.core;

import com.fasterxml.jackson.annotation.JsonRootName;
import org.atteo.evo.inflector.English;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.core.DefaultRelProvider;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Arrays.stream;

@Component
class JsonRootAwareRelProvider implements RelProvider {

    private DefaultRelProvider defaultRelProvider = new DefaultRelProvider();

    @Override
    public String getItemResourceRelFor(Class<?> type) {
        return fromJsonRoot(type).orElseGet(() -> defaultRelProvider.getItemResourceRelFor(type));
    }

    @Override
    public String getCollectionResourceRelFor(Class<?> type) {
        return English.plural(
                fromJsonRoot(type).orElseGet(() -> defaultRelProvider.getCollectionResourceRelFor(type))
        );
    }

    @Override
    public boolean supports(Class<?> delimiter) {
        return defaultRelProvider.supports(delimiter);
    }

    private Optional<String> fromJsonRoot(Class<?> type) {
        return stream(type.getAnnotationsByType(JsonRootName.class))
                .findFirst()
                .map(JsonRootName::value);
    }
}
