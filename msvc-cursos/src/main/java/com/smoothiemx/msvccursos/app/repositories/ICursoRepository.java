package com.smoothiemx.msvccursos.app.repositories;

import com.smoothiemx.msvccursos.app.models.entities.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ICursoRepository extends CrudRepository<Curso, Long> {

    @Modifying
    @Query("delete from CursoUsuario cu where cu.usuarioId = ?1")
    void eliminarCursoUsuarioPorId(Long id);
}