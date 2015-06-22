package io.fourfinanceit.featuros.notification;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
interface NotificationRepository extends PagingAndSortingRepository<Notification, Long> {
}
