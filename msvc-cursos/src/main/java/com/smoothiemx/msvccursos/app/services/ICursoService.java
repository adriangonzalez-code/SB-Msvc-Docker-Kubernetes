package com.smoothiemx.msvccursos.app.services;

import com.smoothiemx.msvccursos.app.models.Usuario;
import com.smoothiemx.msvccursos.app.models.entities.Curso;

import java.util.List;
import java.util.Optional;

public interface ICursoService {

    List<Curso> listar();

    Optional<Curso> porId(Long id);

    Curso guardar(Curso curso);

    void eliminar(Long id);

    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);

    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);

    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);

    Optional<Curso> porIdConUsuarios(Long id);

    void eliminarCursoUsuarioPorId(Long id);
}