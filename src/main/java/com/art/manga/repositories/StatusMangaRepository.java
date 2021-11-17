package com.art.manga.repositories;

import com.art.manga.entities.StatusManga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusMangaRepository extends JpaRepository<StatusManga, Long> {
}
