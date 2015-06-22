package io.fourfinanceit.featuros.deployment;

import io.fourfinanceit.featuros.notification.INotification;
import io.fourfinanceit.featuros.notification.NotificationCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
class NotificationAwareCreator implements ApplicationListener<NotificationCreatedEvent> {

    private final DeploymentRepository repository;

    @Autowired
    NotificationAwareCreator(DeploymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(NotificationCreatedEvent event) {
        repository.save(toDeployment(event.eventObject()));
    }

    private static Deployment toDeployment(INotification notification) {
        return new Deployment(notification.getName(), notification.getProduct(),
                notification.getGroup(), notification.getVersion(), notification.getDate());
    }
}
