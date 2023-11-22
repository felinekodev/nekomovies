package com.autocine.service.repository;

import com.autocine.service.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
  List<Pelicula> findByFechaExhibicion(Date fechaExhibicion);
}