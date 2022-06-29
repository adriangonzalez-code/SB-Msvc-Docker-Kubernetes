package com.smoothiemx.msvcusuarios.app.services;

import com.smoothiemx.msvcusuarios.app.models.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    List<Usuario> listar();

    Optional<Usuario> porId(Long id);

    Usuario guardar(Usuario usuario);

    void eliminar(Long id);

    Optional<Usuario> porEmail(String email);

    boolean existePorEmail(String email);
}