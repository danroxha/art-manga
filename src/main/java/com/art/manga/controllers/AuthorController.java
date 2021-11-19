package com.art.manga.controllers;

import com.art.manga.entities.Author;
import com.art.manga.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("autor")
public class AuthorController {

  public final AuthorService authorService;

  @Autowired
  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }


  @RequestMapping("adicionar")
  public ModelAndView saveAuthor() {
    return new ModelAndView("author/form.html") {{
      addObject("author", new Author());
    }};
  }

  @RequestMapping
  public ModelAndView listAuthors() {
    return new ModelAndView("author/list.html") {{
      addObject("authors", authorService.listAllAuthors());
    }};
  }

  @RequestMapping(method = RequestMethod.POST, path = "adicionar")
  public ModelAndView saveAuthor(Author author, RedirectAttributes redirectAttributes, BindingResult bindingResult) {


    redirectAttributes.addFlashAttribute("message", "Mangá salvo com sucesso");
    redirectAttributes.addFlashAttribute("hasErrors", Boolean.FALSE);

    if(bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("message", "Mangá não foi salvo, corrija os erros abaixo");
      redirectAttributes.addFlashAttribute("hasErrors", Boolean.TRUE);

      redirectAttributes.addFlashAttribute("author", author);

      return new ModelAndView("redirect:/autor/adicionar");
    }

    authorService.saveAuthor(author);

    return new ModelAndView("redirect:/autor/adicionar");
  }

  @RequestMapping(method = RequestMethod.GET, path = "editar")
  public ModelAndView updateAuthor(@RequestParam Long id) {
    var author = authorService.findAuthorById(id);

    if(author.isEmpty()) {
      return new ModelAndView("redirect:/autor");
    }

    return new ModelAndView("author/form.html") {{
      addObject("author", author.get());
    }};
  }

  @RequestMapping(method = RequestMethod.GET, path = "excluir")
  public ModelAndView deleteAuthor(@RequestParam Long id, RedirectAttributes redirectAttributes) {
    var authorDeleted = authorService.deleteAuthorById(id);

    if(authorDeleted.isEmpty()) {
      redirectAttributes.addFlashAttribute("message", String.format("Autor com ID %s não pode ser excluído", authorDeleted.get().getId()));
    }

    redirectAttributes.addFlashAttribute("message", String.format("Autor %s excluído", authorDeleted.get().getName()));

    return new ModelAndView("redirect:/autor");
  }
}
