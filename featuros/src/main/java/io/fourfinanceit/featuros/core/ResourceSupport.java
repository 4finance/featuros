package io.fourfinanceit.featuros.core;

import org.springframework.hateoas.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class ResourceSupport<T extends Identifiable> {

    private final Class<T> clazz;
    private final EntityLinks entityLinks;

    public ResourceSupport(Class<T> clazz, EntityLinks entityLinks) {
        this.clazz = clazz;
        this.entityLinks = entityLinks;
    }

    public Resource<T> resource(T entity) {
        return new Resource<>(entity, link(entity).withSelfRel());
    }

    public Resources<Resource<T>> collection(Iterable<T> entities) {
        List<Resource<T>> resources = StreamSupport.stream(entities.spliterator(), false)
                .map(this::resource)
                .collect(toList());
        return new Resources<>(resources, entityLinks.linkToCollectionResource(clazz).withSelfRel());
    }

    public Link link(T entity) {
        return entityLinks.linkToSingleResource(entity);
    }

    public URI uri(T entity) {
        try {
            return new URI(link(entity).getHref());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
