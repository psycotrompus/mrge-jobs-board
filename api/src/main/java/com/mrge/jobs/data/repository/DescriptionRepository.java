package com.mrge.jobs.data.repository;

import com.mrge.jobs.data.entity.DescriptionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface DescriptionRepository extends ReactiveCrudRepository<DescriptionEntity, Integer> {

  Flux<DescriptionEntity> findByJobId(Integer jobId);
}
