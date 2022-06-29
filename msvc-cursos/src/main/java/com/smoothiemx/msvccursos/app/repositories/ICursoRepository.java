package com.smoothiemx.msvccursos.app.repositories;

import com.smoothiemx.msvccursos.app.entities.Curso;
import org.springframework.data.repository.CrudRepository;

public interface ICursoRepository extends CrudRepository<Curso, Long> {


}