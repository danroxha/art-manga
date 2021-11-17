package com.art.manga.services;

import com.art.manga.entities.PublishingCompany;
import com.art.manga.repositories.PublishingCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublishingCompanyService {

  private final PublishingCompanyRepository publishingCompanyRepository;

  @Autowired
  public PublishingCompanyService(PublishingCompanyRepository publishingCompanyRepository) {
    this.publishingCompanyRepository = publishingCompanyRepository;
  }

  public Optional<PublishingCompany> savePublishingCompany(PublishingCompany publishingCompany) {
    return Optional.of(publishingCompanyRepository.save(publishingCompany));
  }

  public List<PublishingCompany> listAllPublishers() {
    return publishingCompanyRepository.findAll();
  }
}
