package io.fourfinanceit.featuros;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DeploymentRepository extends PagingAndSortingRepository<Deployment, Long> {

}
