package com.mrge.jobs.dto;

public record SubmissionDto(
   Integer jobId,
   String name,
   String email,
   String message
) {}
