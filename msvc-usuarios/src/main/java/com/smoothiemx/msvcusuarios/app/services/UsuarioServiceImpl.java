package com.smoothiemx.msvcusuarios.app.services;

import com.smoothiemx.msvcusuarios.app.models.entities.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Override
    public List<Usuario> listar() {
        return null;
    }

    @Override
    public Optional<Usuario> porId(Long id) {
        return Optional.empty();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }
}
