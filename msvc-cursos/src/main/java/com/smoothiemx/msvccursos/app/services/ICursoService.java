package com.smoothiemx.msvccursos.app.services;

import com.smoothiemx.msvccursos.app.models.entities.Curso;

import java.util.List;
import java.util.Optional;

public interface ICursoService {

    List<Curso> listar();

    Optional<Curso> porId(Long id);

    Curso guardar(Curso curso);

    void eliminar(Long id);
}