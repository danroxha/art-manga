package com.art.manga.services;

import com.art.manga.entities.Author;
import com.art.manga.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

  private final AuthorRepository authorRepository;

  @Autowired
  public AuthorService(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  public Optional<Author> saveAuthor(Author author) {
    return Optional.of(authorRepository.save(author));
  }

  public List<Author> listAllAuthors() {
    return authorRepository.findAll();
  }

  public Optional<Author> findAuthorById(Long id) {
    return authorRepository.findById(id);
  }

  public Optional<Author> deleteAuthorById(Long id) {
    var author = findAuthorById(id);

    if(author.isEmpty()) {
      return Optional.empty();
    }

    authorRepository.deleteById(id);

    return author;
  }
}
