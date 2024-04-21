package com.mrge.jobs.data.repository;

import com.mrge.jobs.data.entity.ApplicationEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ApplicationRepository extends ReactiveCrudRepository<ApplicationEntity, Integer> {

  Flux<ApplicationEntity> findByJobIdAndEmail(Integer jobId, String email);
}
