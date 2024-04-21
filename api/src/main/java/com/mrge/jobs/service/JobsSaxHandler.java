package com.mrge.jobs.service;

import com.mrge.jobs.dto.JobXmlDto;
import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

class JobsSaxHandler extends DefaultHandler {

  @Getter
  private final List<JobXmlDto> jobs = new ArrayList<>();

  private StringBuilder value;

  private boolean inJobDesc = false;

  private boolean inJobDescName = false;

  private boolean inJobDescValue = false;

  private StringBuilder jobDescName;

  private StringBuilder jobDescValue;

  private JobXmlDto.JobXmlDtoBuilder builder;

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    if ("position".equals(qName)) {
      builder = JobXmlDto.builder();
      return;
    }
    if (!"jobDescription".equals(qName)) {
      value = new StringBuilder();
    }
    else {
      inJobDesc = true;
    }
    if (inJobDesc) {
      inJobDescName = "name".equals(qName);
      if (inJobDescName) {
        jobDescName = new StringBuilder();
      }
      inJobDescValue = "value".equals(qName);
      if (inJobDescValue) {
        jobDescValue = new StringBuilder();
      }
    }
    else {
      inJobDescName = false;
      inJobDescValue = false;
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) {
    if (value == null) {
      value = new StringBuilder();
      jobDescName = new StringBuilder();
      jobDescValue = new StringBuilder();
    }
    else {
      if (!inJobDesc) {
        value.append(ch, start, length);
      }
      else {
        if (inJobDescName) {
          jobDescName.append(ch, start, length);
        }
        if (inJobDescValue) {
          jobDescValue.append(ch, start, length);
        }
      }
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) {
    switch (qName) {
      case "id" -> builder.jobId(Long.parseLong(value.toString()));
      case "subcompany" -> builder.subcompany(value.toString());
      case "office" -> builder.office(value.toString());
      case "department" -> builder.department(value.toString());
      case "recruitingCategory" -> builder.recruitingCategory(value.toString());
      case "name" -> {
        if (inJobDesc && inJobDescName) {
          jobDescName.append(value.toString());
          inJobDescName = false;
        }
        else {
          builder.name(value.toString());
        }
      }
      case "employmentType" -> builder.employmentType(value.toString());
      case "seniority" -> builder.seniority(value.toString());
      case "schedule" -> builder.schedule(value.toString());
      case "yearsOfExperience" -> builder.yearsOfExperience(value.toString());
      case "keywords" -> builder.keywords(value.toString());
      case "occupation" -> builder.occupation(value.toString());
      case "occupationCategory" -> builder.occupationCategory(value.toString());
      case "value" -> {
        if (inJobDesc && inJobDescValue) {
          builder.jobDescription(jobDescName.toString().trim(), jobDescValue.toString().trim());
          inJobDescValue = false;
        }
        else {
          jobDescName = null;
          jobDescValue = null;
        }
      }
      case "jobDescription" -> inJobDesc = false;
      case "position" -> jobs.add(builder.build());
    }
  }

}
