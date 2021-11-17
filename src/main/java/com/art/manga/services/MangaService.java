package com.art.manga.services;

import com.art.manga.entities.Manga;
import com.art.manga.repositories.MangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MangaService {

  private final MangaRepository mangaRepository;

  @Autowired
  public MangaService(MangaRepository mangaRepository) {
    this.mangaRepository = mangaRepository;
  }

  public Optional<Manga> saveManga(Manga manga) {
      return Optional.of(mangaRepository.save(manga));
  }

  public List<Manga> listAllMangas() {
    return mangaRepository.findAll();
  }

}
