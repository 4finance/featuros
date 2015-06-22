package io.fourfinanceit.featuros.notification;

import java.time.Instant;

public interface INotification {
    String getName();

    String getProduct();

    String getGroup();

    String getVersion();

    Instant getDate();

    String getAddress();
}
