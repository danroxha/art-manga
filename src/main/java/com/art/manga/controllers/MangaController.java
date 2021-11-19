package com.art.manga.controllers;

import com.art.manga.entities.Manga;
import com.art.manga.services.*;
import com.art.manga.util.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
@RestController
@RequestMapping("manga")
public class MangaController {

  public static final String FOLDER_COVER = "storage/manga/cover";

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
    return new ModelAndView("manga/form.html") {{
      addObject("manga", new Manga());
      addObject("demographics", demographicService.listAllDemographics());
      addObject("genres", genreService.listAllGenres());
      addObject("authors", authorService.listAllAuthors());
      addObject("status", statusMangaService.listAllStatus());
      addObject("publishers", publishingCompanyService.listAllPublishers());
    }};
  }

  @RequestMapping( method = RequestMethod.GET, path = "{id}")
  public ModelAndView showManga(@PathVariable Long id) {
    var manga = mangaService.findMangaById(id);

    if(manga.isEmpty())
      return new ModelAndView("redirect:/manga");


    return new ModelAndView("manga/details.html"){{
      addObject("manga", manga.get());
    }};
  }

  @RequestMapping(method = RequestMethod.GET, path = "editar")
  public ModelAndView updateManga(@RequestParam Long id, RedirectAttributes redirectAttributes) {
    var mangaSaved = mangaService.findMangaById(id);

    if(mangaSaved.isEmpty()) {
      return new ModelAndView("redirect:/manga");
    }

    return new ModelAndView("manga/form.html") {{
      addObject("manga", mangaSaved.get());
      addObject("demographics", demographicService.listAllDemographics());
      addObject("genres", genreService.listAllGenres());
      addObject("authors", authorService.listAllAuthors());
      addObject("status", statusMangaService.listAllStatus());
      addObject("publishers", publishingCompanyService.listAllPublishers());
    }};
  }

  @RequestMapping(method = RequestMethod.GET, path = "excluir")
  public ModelAndView deleteManga(@RequestParam Long id, RedirectAttributes redirectAttributes ) {
    var mangaDeleted = mangaService.deleteMangaById(id);

    if(mangaDeleted.isPresent())
      redirectAttributes.addFlashAttribute("message", String.format("Manga de '%s' foi excluído", mangaDeleted.get().getTitle()));
    else {
      redirectAttributes.addFlashAttribute("message", String.format("Não pode excluir manga com ID '%s' ", id));
    }

    return new ModelAndView("redirect:/manga");
  }

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView listMangas(@RequestParam(required = false) Long page) {
    return new ModelAndView("manga/list.html") {{
      addObject("mangas", mangaService.listAllMangas());
    }};
  }

  @RequestMapping(method = RequestMethod.POST, path = "adicionar")
  public ModelAndView saveManga(Manga manga, @RequestParam("image") MultipartFile multipartFile, RedirectAttributes redirectAttributes) throws IOException {

    redirectAttributes.addFlashAttribute("message", "Mangá salvo com sucesso");
    redirectAttributes.addFlashAttribute("hasErrors", Boolean.FALSE);

    var hasFile = !multipartFile.getOriginalFilename().isBlank();

    if(hasFile) {
      var extensionFile = FileUploadUtil.getFileExtension(multipartFile.getOriginalFilename());
      String coverName = StringUtils.cleanPath(String.format("%s.%s", System.nanoTime(), extensionFile));
      manga.setCover(coverName);

      if(manga.getId() != null)
        FileUploadUtil.deleteDirectory(String.format("%s/%s", FOLDER_COVER, manga.getId()));

      var mangaSaved = mangaService.saveManga(manga);

      var uploadDir = String.format("%s/%s", FOLDER_COVER, manga.getId());

      FileUploadUtil.saveFile(uploadDir, coverName, multipartFile);

      return new ModelAndView(String.format("redirect:/manga/%s", mangaSaved.get().getId()));
    }

    var view = new ModelAndView(
        manga.getId() != null
            ? String.format("redirect:/manga/%s", manga.getId())
            : "redirect:/manga/adicionar"
        );

    var mangaSaved = mangaService.saveManga(manga);

    return view;
  }


  @RequestMapping(method = RequestMethod.GET, path = "json" )
  public List<Manga> listDevelopers(@RequestParam(required = false) String title ) {

    if(title != null) {
      byte[] decodedBytes = Base64.getDecoder().decode(title);
      String param = new String(decodedBytes);
      return mangaService.findMangasByTitle(param);
    }

    return mangaService.listAllMangas();
  }
}
