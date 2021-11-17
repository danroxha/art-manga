package com.art.manga.services;

import com.art.manga.entities.Genre;
import com.art.manga.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

  private final GenreRepository genreRepository;

  @Autowired
  public GenreService(GenreRepository genreRepository) {
    this.genreRepository = genreRepository;
  }

  public Optional<Genre> saveGenre(Genre genre) {
    return Optional.of(genreRepository.save(genre));
  }

  public List<Genre> listAllGenres() {
    return genreRepository.findAll();
  }
}
