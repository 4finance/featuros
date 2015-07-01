package io.fourfinanceit.featuros.notification;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class TestData implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${server.port}")
    private int port;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        asList(
                new CreateNotification("integration-post-smscredit-lt", "integration-post-lt", "lt", "1.2.3"),
                new CreateNotification("integration-post-vivus-lt", "integration-post-lt", "lt", "1.2.3"),
                new CreateNotification("backoffice-smscredit-lt", "backoffice", "lt", "1.2.4"),
                new CreateNotification("web-smscredit-lt", "web", "lt", "1.2.4"),
                new CreateNotification("backoffice-vivus-lt", "backoffice", "lt", "1.2.4"),
                new CreateNotification("web-vivus-lt", "web", "lt", "1.2.4"),
                new CreateNotification("backoffice-zaplo-pl", "backoffice", "pl", "1.2.4")
        ).forEach(this::postToApi);
    }

    private void postToApi(CreateNotification notification) {
        new RestTemplate().postForObject(format("http://localhost:%d/api/notifications", port), notification, Object.class);
    }
}
