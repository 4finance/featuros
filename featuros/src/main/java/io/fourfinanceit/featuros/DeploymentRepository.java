package io.fourfinanceit.featuros;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeploymentRepository extends PagingAndSortingRepository<Deployment, Long> {
}
