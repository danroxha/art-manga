package com.art.manga.services;

import com.art.manga.entities.Manga;
import com.art.manga.repositories.MangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.art.manga.util.FileUploadUtil;

import java.util.List;
import java.util.Optional;

@Service
public class MangaService {

  public static final String FOLDER_COVER = "storage/manga/cover";

  private final MangaRepository mangaRepository;

  @Autowired
  public MangaService(MangaRepository mangaRepository) {
    this.mangaRepository = mangaRepository;
  }

  public Optional<Manga> saveManga(Manga manga) {
      return Optional.of(mangaRepository.save(manga));
  }

  public Optional<Manga> findMangaById(Long id) {
    return mangaRepository.findById(id);
  }

  public List<Manga> listAllMangas() {
    return mangaRepository.findAll();
  }

  public Optional<Manga> deleteMangaById(Long id) {
    try {
      var mangaSaved = findMangaById(id);

      if(mangaSaved.isEmpty()) return Optional.empty();

      mangaRepository.deleteById(id);

      FileUploadUtil.deleteDirectory(String.format("%s/%s", FOLDER_COVER, mangaSaved.get().getId()));

      return mangaSaved;

    }catch (Exception e) {
      return Optional.empty();
    }
  }

  public List<Manga> findMangasByTitle(String title) {
    return mangaRepository.findByTitle(title);
  }
}
