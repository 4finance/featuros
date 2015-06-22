package io.fourfinanceit.featuros;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.util.Arrays.asList;

@Component
public class TestData implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        asList(
                new CreateNotification("integration-post-smscredit-lt", "integration-post", "lt", "1.2.3"),
                new CreateNotification("backoffice-smscredit-lt", "backoffice", "lt", "1.2.4")
        ).forEach(this::postToApi);
    }

    private void postToApi(CreateNotification notification) {
        new RestTemplate().postForObject("http://localhost:8080/api/notifications", notification, Object.class);
    }
}
