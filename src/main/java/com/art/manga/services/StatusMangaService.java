package com.art.manga.services;

import com.art.manga.entities.StatusManga;
import com.art.manga.repositories.StatusMangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusMangaService {

  private final StatusMangaRepository statusMangaRepository;

  @Autowired
  public StatusMangaService(StatusMangaRepository statusMangaRepository) {
    this.statusMangaRepository = statusMangaRepository;
  }

  public Optional<StatusManga> saveStatus(StatusManga statusManga) {
    return Optional.of(statusMangaRepository.save(statusManga));
  }

  public List<StatusManga> listAllStatus() {
    return statusMangaRepository.findAll();
  }
}
