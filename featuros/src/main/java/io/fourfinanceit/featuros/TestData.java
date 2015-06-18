package io.fourfinanceit.featuros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static java.util.Arrays.asList;

@Component
public class TestData {

    @Autowired
    private DeploymentRepository deploymentRepository;

    @PostConstruct
    public void load() {
        asList(
                new Deployment("integration-post-smscredit-lt", "integration-post", "lt", "1.2.3"),
                new Deployment("backoffice-smscredit-lt", "backoffice", "lt", "1.2.4")
        ).forEach(deploymentRepository::save);
    }

}
