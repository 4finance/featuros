package io.fourfinanceit.featuros.deployment;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
interface DeploymentRepository extends PagingAndSortingRepository<Deployment, Long> {
}
