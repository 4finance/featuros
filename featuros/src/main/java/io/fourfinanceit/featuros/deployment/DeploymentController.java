package io.fourfinanceit.featuros.deployment;

import io.fourfinanceit.featuros.core.ResourceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@ExposesResourceFor(Deployment.class)
@RequestMapping(value = "/api/deployments")
class DeploymentController {

    private final DeploymentRepository repository;
    private final ResourceSupport<Deployment> resources;

    @Autowired
    DeploymentController(DeploymentRepository repository, EntityLinks entityLinks) {
        this.repository = repository;
        this.resources = new ResourceSupport<>(Deployment.class, entityLinks);
    }

    @RequestMapping(method = GET)
    public ResponseEntity list() {
        return ok(resources.collection(repository.findAll()));
    }

    @RequestMapping(method = GET, path = "/{deploymentId}")
    public ResponseEntity show(@PathVariable("deploymentId") Long id) {
        Deployment deployment = repository.findOne(id);
        return deployment != null
                ? ok(resources.resource(deployment))
                : notFound().build();
    }
}
