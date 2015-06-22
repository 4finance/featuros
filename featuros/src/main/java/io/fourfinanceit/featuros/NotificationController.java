package io.fourfinanceit.featuros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static java.time.Instant.now;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/api/notifications")
@ExposesResourceFor(Notification.class)
class NotificationController {

    private final NotificationRepository repository;
    private final EntityLinks entityLinks;

    @Autowired
    NotificationController(NotificationRepository repository, EntityLinks entityLinks) {
        this.repository = repository;
        this.entityLinks = entityLinks;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Void> create(@RequestBody CreateNotification notification, HttpServletRequest request) throws URISyntaxException {
        URI createdUri = notification.chain()
                .map(n -> new Notification(n.getName(), n.getProduct(), n.getGroup(), n.getVersion(), now(), request.getRemoteAddr()))
                .map(repository::save)
                .map(n -> link(n).toUri())
                .get();
        return created(createdUri).build();
    }

    @RequestMapping(method = GET)
    public ResponseEntity<Resources<Resource<Notification>>> list() {
        Iterable<Notification> notifications = repository.findAll();
        List<Resource<Notification>> resources = stream(notifications.spliterator(), false).map(this::toResource).collect(toList());
        Resources<Resource<Notification>> wrapped = new Resources<>(resources, entityLinks.linkToCollectionResource(Notification.class).withSelfRel());
        return ok(wrapped);
    }

    @RequestMapping(method = GET, path = "/{notificationId}")
    public ResponseEntity show(@PathVariable("notificationId") Long id) {
        Notification notification = repository.findOne(id);
        return notification != null
                ? ok(toResource(notification))
                : notFound().build();
    }

    private <T extends Identifiable<Long>> Resource<T> toResource(T entity) {
        return new Resource<>(entity, link(entity).withSelfRel());
    }

    private LinkBuilder link(Identifiable<Long> identifiable) {
        return entityLinks.linkForSingleResource(identifiable);
    }

}
