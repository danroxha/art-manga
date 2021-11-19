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

  public Optional<Genre> deleteGenreById(Long id) {

    var genreSaved = genreRepository.findById(id);

    if(genreSaved.isEmpty()) {
      return Optional.empty();
    }
    try {
      genreRepository.deleteById(id);
      return genreSaved;
    }
    catch (Exception e) {
      return Optional.empty();
    }
  }
}
