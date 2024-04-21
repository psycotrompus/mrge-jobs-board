package com.mrge.jobs.service;

import com.mrge.jobs.data.entity.ApplicationEntity;
import com.mrge.jobs.data.repository.ApplicationRepository;
import com.mrge.jobs.data.repository.DescriptionRepository;
import com.mrge.jobs.data.repository.JobRepository;
import com.mrge.jobs.dto.ApplicationDto;
import com.mrge.jobs.dto.DescriptionDto;
import com.mrge.jobs.dto.JobDetailsDto;
import com.mrge.jobs.dto.JobHeaderDto;
import com.mrge.jobs.dto.SubmissionDto;
import com.mrge.jobs.exception.JobApplicationException;
import com.mrge.jobs.exception.JobNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class DefaultJobService implements JobService {

  private final JobRepository jobRepo;

  private final DescriptionRepository descRepo;

  private final ApplicationRepository appRepo;

  private final ApplicationEventPublisher publisher;

  private String jobBaseUrl;

  @Value("${jobs.base-url}")
  void setJobBaseUrl(String jobBaseUrl) {
    this.jobBaseUrl = jobBaseUrl;
  }

  @Override
  public Flux<JobHeaderDto> getJobs() {
    return jobRepo.findAll().flatMap(job -> {
      var builder = JobHeaderDto.builder()
          .id(job.getId())
          .jobId(job.getJobId())
          .office(job.getOffice())
          .url(jobBaseUrl + job.getId())
          .name(job.getName());
      return descRepo.findByJobId(job.getId())
          .doOnNext(desc -> builder.description(new DescriptionDto(desc.getId(), desc.getName(), desc.getDescription())))
          .collectList().map(x -> builder.build());
    });
  }

  @Override
  public Mono<JobDetailsDto> getJobDetails(Integer id) {
    var jobMono = jobRepo.findById(id).map(job -> JobDetailsDto.builder()
        .id(job.getId())
        .jobId(job.getJobId())
        .name(job.getName())
        .subcompany(job.getSubcompany())
        .office(job.getOffice())
        .department(job.getDepartment())
        .recruitingCategory(job.getRecruitingCategory())
        .employmentType(job.getEmploymentType())
        .seniority(job.getSeniority())
        .schedule(job.getSchedule())
        .yearsOfExperience(job.getYearsOfExperience())
        .keywords(job.getKeywords())
        .occupation(job.getOccupation())
        .occupationCategory(job.getOccupationCategory()));
    var descMono = descRepo.findByJobId(id)
        .map(desc -> new DescriptionDto(desc.getId(), desc.getName(), desc.getDescription()))
        .collectList();
    return Mono.zip(jobMono, descMono).map(tuple -> {
      var builder = tuple.getT1();
      tuple.getT2().forEach(builder::description);
      return builder.build();
    });
  }

  @Override
  public Mono<Void> apply(SubmissionDto submission) {
    return jobRepo.findById(submission.jobId())
        .flatMap(job -> appRepo.findByJobIdAndEmail(submission.jobId(), submission.email())
            .collectList()
            .<ApplicationEntity>handle((apps, sink) -> {
              if (apps == null || apps.isEmpty()) {
                sink.next(ApplicationEntity.builder()
                    .jobId(job.getId())
                    .name(submission.name())
                    .email(submission.email())
                    .message(submission.message())
                    .submittedOn(LocalDateTime.now())
                    .state(ApplicationState.PENDING.ordinal())
                    .build());
              }
              else {
                sink.error(new JobApplicationException("Already applied."));
              }
            })
            .flatMap(appRepo::save))
        .doOnNext(app -> {
          var dto = ApplicationDto.builder()
              .id(app.getId())
              .jobId(app.getJobId())
              .name(app.getName())
              .email(app.getEmail())
              .message(app.getMessage())
              .submittedOn(app.getSubmittedOn())
              .state(ApplicationState.values()[app.getState()])
              .build();
          publisher.publishEvent(new SubmittedEvent(DefaultJobService.this, dto));
        })
        .switchIfEmpty(Mono.error(new JobNotFoundException("Job not found.")))
        .then();
  }

  @Override
  public Flux<ApplicationDto> getApplications() {
    return appRepo.findAll().map(app -> ApplicationDto.builder()
        .id(app.getId())
        .jobId(app.getJobId())
        .name(app.getName())
        .email(app.getEmail())
        .message(app.getMessage())
        .submittedOn(app.getSubmittedOn())
        .state(ApplicationState.values()[app.getState()])
        .build());
  }

  @Override
  public Mono<Void> confirm(Integer appId, Integer state) {
    return appRepo.findById(appId)
        .<ApplicationEntity>handle((app, sink) -> {
          var appState = ApplicationState.from(state);
          if (appState == null) {
            sink.error(new JobApplicationException("Invalid application status."));
          }
          else {
            app.setState(appState.ordinal());
            sink.next(app);
          }
        })
        .flatMap(appRepo::save)
        .then();
  }
}
