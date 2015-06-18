package io.fourfinanceit.featuros;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long> {
}
