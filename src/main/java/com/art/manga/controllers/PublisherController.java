package com.art.manga.controllers;

import com.art.manga.entities.PublishingCompany;
import com.art.manga.services.PublishingCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("editora")
public class PublisherController {

  private final PublishingCompanyService publishingCompanyService;

  @Autowired
  public PublisherController(PublishingCompanyService publishingCompanyService) {
    this.publishingCompanyService = publishingCompanyService;
  }

  @RequestMapping
  public ModelAndView listPublishers() {
    return new ModelAndView("publisher/list.html") {{
      addObject("publishers", publishingCompanyService.listAllPublishers());
    }};
  }

  @RequestMapping(method = RequestMethod.GET, path = "editar")
  public ModelAndView updatePublishers(@RequestParam Long id) {
    var publisher = publishingCompanyService.findPublisherById(id);

    if(publisher.isEmpty()) {
      return new ModelAndView("redirect:/editora");
    }

    return new ModelAndView("publisher/form.html") {{
      addObject("publisher", publisher.get());
    }};
  }

  @RequestMapping(method = RequestMethod.GET, path = "adicionar")
  public ModelAndView savePublisher() {
    return new ModelAndView("publisher/form.html") {{
      addObject("publisher", new PublishingCompany());
    }};
  }

  @RequestMapping(method = RequestMethod.POST, path = "adicionar")
  public ModelAndView savePublisher(PublishingCompany publishingCompany, RedirectAttributes redirectAttributes, BindingResult bindingResult) {

    redirectAttributes.addFlashAttribute("message", "Editora salvo com sucesso");
    redirectAttributes.addFlashAttribute("hasErrors", Boolean.FALSE);

    if(bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("message", "Mangá não foi salvo, corrija os erros abaixo");
      redirectAttributes.addFlashAttribute("hasErrors", Boolean.TRUE);
      return new ModelAndView("publisher/form.html") {{
        addObject("publisher", publishingCompany);
      }};
    }

    publishingCompanyService.savePublishingCompany(publishingCompany);

    return new ModelAndView("redirect:/editora/adicionar");

  }

  @RequestMapping(method = RequestMethod.GET, path = "excluir")
  public ModelAndView deletePublisher(@RequestParam  Long id, RedirectAttributes redirectAttributes) {
    var publisherDeleted = publishingCompanyService.detetePublisherById(id);

    if(publisherDeleted.isEmpty()) {
      redirectAttributes.addFlashAttribute("message", String.format("Não pode excluir editora com ID %s", id));
      redirectAttributes.addFlashAttribute("hasErrors", Boolean.TRUE);
    }
    else {
      redirectAttributes.addFlashAttribute("message", String.format("Editora %s excluído", publisherDeleted.get().getName()));
    }

    return new ModelAndView("redirect:/editora");

  }
}
