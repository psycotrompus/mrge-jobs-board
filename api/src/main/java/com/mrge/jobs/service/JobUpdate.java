package com.mrge.jobs.service;

import com.mrge.jobs.data.entity.DescriptionEntity;
import com.mrge.jobs.data.entity.JobEntity;
import com.mrge.jobs.data.repository.DescriptionRepository;
import com.mrge.jobs.data.repository.JobRepository;
import com.mrge.jobs.dto.JobXmlDto;
import com.mrge.jobs.exception.JobUpdateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
class JobUpdate {

  private final WebClient.Builder builder;

  private final JobRepository jobRepo;

  private final DescriptionRepository descRepo;

  Mono<Long> run(String url) {
    log.info("run(String) - START - url={}", url);
    var publisher = fetchJobs(url)
        .flatMapMany(this::parseJobs)
        .flatMap(job -> jobRepo.findByJobId(job.getJobId())
            .switchIfEmpty(Mono.just(JobEntity.builder()
                .jobId(job.getJobId())
                .subcompany(job.getSubcompany())
                .office(job.getOffice())
                .department(job.getDepartment())
                .recruitingCategory(job.getRecruitingCategory())
                .name(job.getName())
                .employmentType(job.getEmploymentType())
                .seniority(job.getSeniority())
                .schedule(job.getSchedule())
                .yearsOfExperience(job.getYearsOfExperience())
                .keywords(job.getKeywords())
                .occupation(job.getOccupation())
                .occupationCategory(job.getOccupationCategory())
                .build())
              .flatMap(jobRepo::save)
              .flatMap(j -> Flux.fromIterable(job.getJobDescriptions().entrySet())
                  .map(entry -> {
                    var desc = new DescriptionEntity();
                    desc.setJobId(j.getId());
                    desc.setName(entry.getKey());
                    desc.setDescription(entry.getValue());
                    return desc;
                  })
                  .flatMap(descRepo::save)
                  .collectList().map(x -> j))
            )
        )
        .count();
    log.info("run(String) - END");
    return publisher;
  }

  private Mono<InputStream> fetchJobs(String url) {
    log.info("fetchJobs(String) - START - url={}", url);
    var client = builder.baseUrl(url).build();
    var publisher = client.get().retrieve().toEntity(Resource.class)
        .<InputStream>handle((res, sink) -> {
          if (!res.getStatusCode().is2xxSuccessful()) {
            sink.error(new JobUpdateException("Failed to retrieve job postings."));
          }
          try {
            sink.next(res.getBody().getInputStream());
          }
          catch (IOException ex) {
            sink.error(new JobUpdateException("Failed to retrieve response body.", ex));
          }
        });
    log.info("fetchJobs(String) - END");
    return publisher;
  }

  private Flux<JobXmlDto> parseJobs(InputStream is) {
    log.info("parseJobs(InputStream) - START");
    try {
      var factory = SAXParserFactory.newInstance();
      var parser = factory.newSAXParser();
      var handler = new JobsSaxHandler();
      parser.parse(is, handler);
      var jobs = handler.getJobs();
      log.debug("parseJobs(InputStream) - Found jobs: {}", jobs);
      log.info("parseJobs(InputStream) - END");
      return Flux.fromIterable(jobs);
    }
    catch (Exception ex) {
      log.error("Unable to parse jobs.", ex);
      throw new JobUpdateException("Unable to parse jobs.", ex);
    }
  }
}
