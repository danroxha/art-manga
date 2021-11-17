package com.art.manga.entities.enums;

public enum MangaGenre {
  DOUJINSHI("Doujinshi"),
  GEKIGA("Gekiga"),
  HENTAI("Hentai"),
  JOSEI("Josei"),
  SEINEN("Seinen"),
  SHOUJO("Shoujo"),
  SHONEN("Shonen"),
  KODOMO("Kodomo"),
  YURI("Yuri"),
  YAOI("Yaoi"),
  OTHER("Other");

  MangaGenre() {
    this("Any");
  }

  MangaGenre(String description) {
    this.description = description;
  }

  private final String description;
  public String getDescription() {
    return description;
  }
}
