package io.fourfinanceit.featuros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;
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
    public ResponseEntity<Void> post(@RequestBody Notification notification) throws URISyntaxException {
        Notification saved = repository.save(notification);
        return created(link(saved).toUri()).build();
    }

    @RequestMapping(method = GET)
    public ResponseEntity<Resources<Resource<Notification>>> list() {
        List<Resource<Notification>> resources = stream(repository.findAll().spliterator(), false)
                .map(it -> new Resource<>(it, link(it).withSelfRel()))
                .collect(toList());
        Resources<Resource<Notification>> wrapped = new Resources<>(resources, entityLinks.linkToCollectionResource(Notification.class).withSelfRel());
        return ok(wrapped);
    }

    @RequestMapping(method = GET, path = "/{notificationId}")
    public ResponseEntity<Resource<Notification>> show(@PathVariable("notificationId") Long id) {
        Notification notification = repository.findOne(id);
        Resource<Notification> resource = new Resource<>(notification, link(notification).withSelfRel());
        return ok(resource);
    }

    private LinkBuilder link(Notification notification) {
        return entityLinks.linkForSingleResource(notification.getClass(), notification.getId());
    }

}
