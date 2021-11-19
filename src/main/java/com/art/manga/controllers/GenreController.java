package com.art.manga.controllers;

import com.art.manga.entities.Genre;
import com.art.manga.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("genero")
public class GenreController {

  private final GenreService genreService;

  @Autowired
  public GenreController(GenreService genreService) {
    this.genreService = genreService;
  }

  @RequestMapping
  public ModelAndView listGenres() {
    return new ModelAndView("genre/list.html") {{
      addObject("genres", genreService.listAllGenres());
    }};
  }

  @RequestMapping("adicionar")
  public ModelAndView saveGenre() {
    return new ModelAndView("genre/form.html") {{
      addObject("genre", new Genre());
    }};
  }

  @RequestMapping(method = RequestMethod.POST, path = "adicionar")
  public ModelAndView saveGenre(Genre genre, RedirectAttributes redirectAttributes, BindingResult bindingResult) {

    redirectAttributes.addFlashAttribute("message", "Gênero salvo com sucesso");
    redirectAttributes.addFlashAttribute("hasErrors", Boolean.FALSE);

    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("message", "Gênero não foi salvo, corrija os erros abaixo");
      redirectAttributes.addFlashAttribute("hasErrors", Boolean.TRUE);

      redirectAttributes.addFlashAttribute("genre", genre);

      return new ModelAndView("redirect:/genero/adicionar");
    }

    genreService.saveGenre(genre);

    return new ModelAndView("redirect:/genero/adicionar");
  }

  @RequestMapping(method = RequestMethod.GET, path = "excluir")
  public ModelAndView deleteGenre(@RequestParam Long id, RedirectAttributes redirectAttributes) {
    var deletedGenre = genreService.deleteGenreById(id);

    redirectAttributes.addFlashAttribute("hasErrors", Boolean.FALSE);

    if(deletedGenre.isEmpty()) {
      redirectAttributes.addFlashAttribute("message", String.format("Não pode excluir genero com ID %s", id));
      redirectAttributes.addFlashAttribute("hasErrors", Boolean.TRUE);
    }
    else {
      redirectAttributes.addFlashAttribute("message", String.format("Gênero %s excluído", deletedGenre.get().getName()));
    }

    return new ModelAndView("redirect:/genero");
  }
}
