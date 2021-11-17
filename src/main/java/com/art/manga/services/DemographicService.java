package com.art.manga.services;

import com.art.manga.entities.Demographic;
import com.art.manga.repositories.DemographicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemographicService {

  private final DemographicRepository demographicRepository;

  @Autowired
  public DemographicService(DemographicRepository demographicRepository) {
    this.demographicRepository = demographicRepository;
  }

  public Optional<Demographic> saveDemographic(Demographic demographic) {
    return Optional.of(demographicRepository.save(demographic));
  }

  public List<Demographic> listAllDemographics() {
    return demographicRepository.findAll();
  }
}
