package io.fourfinanceit.featuros.notification;

import io.fourfinanceit.featuros.core.ResourceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

import static java.time.Instant.now;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/api/notifications")
@ExposesResourceFor(Notification.class)
class NotificationController {

    private final NotificationRepository repository;
    private final ResourceSupport<Notification> resources;

    @Autowired
    NotificationController(NotificationRepository repository, EntityLinks entityLinks) {
        this.repository = repository;
        this.resources = new ResourceSupport<>(Notification.class, entityLinks);
    }

    @RequestMapping(method = POST)
    public ResponseEntity<Void> create(@RequestBody CreateNotification notification, HttpServletRequest request) throws URISyntaxException {
        URI createdUri = notification.chain()
                .map(n -> new Notification(n.getName(), n.getProduct(), n.getGroup(), n.getVersion(), now(), request.getRemoteAddr()))
                .map(repository::save)
                .map(n -> resources.link(n).toUri())
                .get();
        return created(createdUri).build();
    }

    @RequestMapping(method = GET)
    public ResponseEntity<Resources<Resource<Notification>>> list() {
        return ok(resources.collection(repository.findAll()));
    }

    @RequestMapping(method = GET, path = "/{notificationId}")
    public ResponseEntity show(@PathVariable("notificationId") Long id) {
        Notification notification = repository.findOne(id);
        return notification != null
                ? ok(resources.resource(notification))
                : notFound().build();
    }

}
