package com.mrge.jobs.data.repository;

import com.mrge.jobs.data.entity.JobEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface JobRepository extends ReactiveCrudRepository<JobEntity, Integer> {

  Mono<JobEntity> findByJobId(Long jobId);
}
