package io.fourfinanceit.featuros;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/deployments")
@RestController
public class DeploymentResource {

    @RequestMapping(method = GET)
    public List<Deployment> all() {
        List<Deployment> deployments = new ArrayList<Deployment>();
        deployments.add(new Deployment("integration-post-smscredit-lt", "integration-post", "lt", "1.2.3"));
        deployments.add(new Deployment("backoffice-smscredit-lt", "backoffice", "lt", "1.2.4"));
        return deployments;
    }

}
