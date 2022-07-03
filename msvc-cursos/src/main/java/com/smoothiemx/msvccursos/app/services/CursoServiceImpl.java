package com.smoothiemx.msvccursos.app.services;

import com.smoothiemx.msvccursos.app.clients.UsuarioClientRest;
import com.smoothiemx.msvccursos.app.models.Usuario;
import com.smoothiemx.msvccursos.app.models.entities.Curso;
import com.smoothiemx.msvccursos.app.models.entities.CursoUsuario;
import com.smoothiemx.msvccursos.app.repositories.ICursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements ICursoService {

    @Autowired
    private ICursoRepository repository;

    @Autowired
    private UsuarioClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>) this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return this.repository.findById(id);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return this.repository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = this.repository.findById(cursoId);

        if (o.isPresent()) {
            Usuario usuarioMsvc = this.client.detalle(usuario.getId());

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.addCursoUsuario(cursoUsuario);
            this.repository.save(curso);
            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = this.repository.findById(cursoId);

        if (o.isPresent()) {
            Usuario usuarioNuevoMsvc = this.client.crear(usuario);

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());

            curso.addCursoUsuario(cursoUsuario);
            this.repository.save(curso);
            return Optional.of(usuarioNuevoMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = this.repository.findById(cursoId);

        if (o.isPresent()) {
            Usuario usuarioMsvc = this.client.detalle(usuario.getId());

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.removeCursoUsuario(cursoUsuario);
            this.repository.save(curso);
            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porIdConUsuarios(Long id) {
        Optional<Curso> o = this.repository.findById(id);

        if (o.isPresent()) {
            Curso curso = o.get();

            if (!curso.getCursoUsuarios().isEmpty()) {
                Iterable<Long> ids = curso.getCursoUsuarios().stream().map(CursoUsuario::getUsuarioId).collect(Collectors.toList());

                List<Usuario> usuarios = this.client.obtenerAlumnosPorCurso(ids);

                curso.setUsuarios(usuarios);
            }

            return Optional.of(curso);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long id) {
        this.repository.eliminarCursoUsuarioPorId(id);
    }
}