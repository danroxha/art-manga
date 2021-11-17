package com.art.manga.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Manga {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @Lob
  private String synopsis;
  private String cover;
  private Integer chapters;

  @OneToMany
  private List<Author> authors;

  @ManyToOne
  private StatusManga status;

  @ManyToOne
  private Demographic demography;

  @ManyToOne
  private PublishingCompany publishingCompany;

  @OneToMany
  private List<Genre> genres;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSynopsis() {
    return synopsis;
  }

  public void setSynopsis(String synopsis) {
    this.synopsis = synopsis;
  }

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public Integer getChapters() {
    return chapters;
  }

  public void setChapters(Integer chapters) {
    this.chapters = chapters;
  }

  public List<Author> getAuthors() {
    return authors;
  }

  public void setAuthors(List<Author> authors) {
    this.authors = authors;
  }

  public StatusManga getStatus() {
    return status;
  }

  public void setStatus(StatusManga status) {
    this.status = status;
  }

  public Demographic getDemography() {
    return demography;
  }

  public void setDemography(Demographic demography) {
    this.demography = demography;
  }

  public PublishingCompany getPublishingCompany() {
    return publishingCompany;
  }

  public void setPublishingCompany(PublishingCompany publishingCompany) {
    this.publishingCompany = publishingCompany;
  }

  public List<Genre> getGenres() {
    return genres;
  }

  public void setGenres(List<Genre> genres) {
    this.genres = genres;
  }
}
