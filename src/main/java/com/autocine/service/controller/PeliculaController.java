package com.autocine.service.controller;

import com.autocine.service.entity.Pelicula;
import com.autocine.service.service.PeliculasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/movies")
public class PeliculaController {

  @Autowired
  private PeliculasService peliculaService;

  @GetMapping
  public String mostrarTodasLasPeliculas(Model model) {
    List<Pelicula> todasLasPeliculas = peliculaService.getAllPeliculas();
    model.addAttribute("peliculas", todasLasPeliculas);
    return "list-movies";
  }

  @GetMapping("/filter")
  public String filtrarPeliculasPorFecha(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaExhibicion, Model model) {
    List<Pelicula> peliculasFiltradas = peliculaService.getPeliculasByFechaExhibicion(fechaExhibicion);
    if (peliculasFiltradas.isEmpty()) {
      model.addAttribute("mensaje", "No hay películas para la fecha seleccionada.");
    } else {
      model.addAttribute("peliculas", peliculasFiltradas);
    }
    return "/list-movies";
  }


  @GetMapping("/create")
  public String mostrarFormularioCreacionPelicula(Model model) {
    model.addAttribute("pelicula", new Pelicula());
    return "form-movie";
  }

  @PostMapping("/create")
  public String crearPelicula(@ModelAttribute Pelicula pelicula, BindingResult result, Model model) {
    if (result.hasErrors()) {
      // Manejar errores de validación, si es necesario
      System.out.println(result);
      return "/form-movie";
    }

    peliculaService.savePelicula(pelicula);
    return "redirect:/movies";
  }

}