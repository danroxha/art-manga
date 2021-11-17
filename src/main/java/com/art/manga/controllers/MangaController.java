package com.art.manga.controllers;

import com.art.manga.entities.Manga;
import com.art.manga.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RestController
@RequestMapping("manga")
public class MangaController {

  private final MangaService mangaService;
  private final PublishingCompanyService publishingCompanyService;
  private final AuthorService authorService;
  private final GenreService genreService;
  private final StatusMangaService statusMangaService;
  private final DemographicService demographicService;

  public MangaController(MangaService mangaService, PublishingCompanyService publishingCompanyService, AuthorService authorService, GenreService genreService, StatusMangaService statusMangaService, DemographicService demographicService) {
    this.mangaService = mangaService;
    this.publishingCompanyService = publishingCompanyService;
    this.authorService = authorService;
    this.genreService = genreService;
    this.statusMangaService = statusMangaService;
    this.demographicService = demographicService;
  }

  @RequestMapping(method = RequestMethod.GET, path = "adicionar")
  public ModelAndView saveManga() {
    return new ModelAndView("manga/form.html"){{
      addObject("manga", new Manga());
      addObject("demographics", demographicService.listAllDemographics());
      addObject("genres", genreService.listAllGenres());
      addObject("authors", authorService.listAllAuthors());
      addObject("status", statusMangaService.listAllStatus());
      addObject("publishers", publishingCompanyService.listAllPublishers());
    }};
  }

  @RequestMapping(method = RequestMethod.POST, path = "adicionar")
  public ModelAndView saveManga(Manga manga, RedirectAttributes redirectAttributes) {

    redirectAttributes.addFlashAttribute("message", "Mangá salvo com sucesso");
    redirectAttributes.addFlashAttribute("hasErrors", Boolean.FALSE);

    mangaService.saveManga(manga);

    return new ModelAndView("redirect:/manga/adicionar");
  }

  @RequestMapping(method = RequestMethod.GET, path = "json")
  public List<Manga> listMangas() {
    return mangaService.listAllMangas();
  }
}
