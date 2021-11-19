package com.art.manga.repositories;

import com.art.manga.entities.Manga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MangaRepository extends JpaRepository<Manga, Long> {

  @Query("SELECT m FROM Manga  m WHERE m.title  LIKE %:title%")
  List<Manga> findByTitle(@Param("title") String title);
}
