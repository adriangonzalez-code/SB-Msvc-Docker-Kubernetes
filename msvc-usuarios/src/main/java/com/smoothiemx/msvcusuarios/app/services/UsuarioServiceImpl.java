package com.smoothiemx.msvcusuarios.app.services;

import com.smoothiemx.msvcusuarios.app.client.CursoClienteRest;
import com.smoothiemx.msvcusuarios.app.models.entities.Usuario;
import com.smoothiemx.msvcusuarios.app.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository repository;

    @Autowired
    private CursoClienteRest client;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return (List<Usuario>) this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porId(Long id) {
        return this.repository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return this.repository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        this.repository.deleteById(id);
        this.client.eliminarCursoUsuarioPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porEmail(String email) {
        return this.repository.porEmail(email);
    }

    @Override
    public boolean existePorEmail(String email) {
        return this.repository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarPorId(Iterable<Long> ids) {
        return (List<Usuario>) this.repository.findAllById(ids);
    }
}