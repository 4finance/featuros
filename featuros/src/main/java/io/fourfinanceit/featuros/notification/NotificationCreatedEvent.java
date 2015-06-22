package io.fourfinanceit.featuros.notification;

import io.fourfinanceit.featuros.core.TypedEvent;

public class NotificationCreatedEvent extends TypedEvent<INotification> {

    public NotificationCreatedEvent(INotification source) {
        super(source);
    }

}
