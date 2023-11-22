package com.autocine.service.service;

import com.autocine.service.entity.Pelicula;
import com.autocine.service.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PeliculasService {

  @Autowired
  private PeliculaRepository peliculaRepository;

  @Transactional(readOnly = true)
  public List<Pelicula> getAllPeliculas() {
    return peliculaRepository.findAll();
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void savePelicula(Pelicula pelicula) {
    peliculaRepository.save(pelicula);
  }

  @Transactional(readOnly = true)
  public List<Pelicula> getPeliculasByFechaExhibicion(Date fechaExhibicion) {
    return peliculaRepository.findByFechaExhibicion(fechaExhibicion);
  }
}