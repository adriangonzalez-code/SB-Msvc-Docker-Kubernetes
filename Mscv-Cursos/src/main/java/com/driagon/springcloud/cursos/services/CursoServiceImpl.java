package com.driagon.springcloud.cursos.services;

import com.driagon.springcloud.cursos.clients.UsuarioClientRest;
import com.driagon.springcloud.cursos.models.Usuario;
import com.driagon.springcloud.cursos.models.entity.Curso;
import com.driagon.springcloud.cursos.models.entity.CursoUsuario;
import com.driagon.springcloud.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository repository;

    @Autowired
    private UsuarioClientRest cliente;

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
    @Transactional(readOnly = true)
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = this.repository.findById(cursoId);

        if (o.isPresent()) {
            Usuario usuarioMsvc = this.cliente.detalle(usuario.getId());

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
    @Transactional(readOnly = true)
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = this.repository.findById(cursoId);

        if (o.isPresent()) {
            Usuario usuarioMsvc = this.cliente.crear(usuario);

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
    @Transactional(readOnly = true)
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = this.repository.findById(cursoId);

        if (o.isPresent()) {
            Usuario usuarioMsvc = this.cliente.detalle(usuario.getId());

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.removeCursoUsuario(cursoUsuario);
            this.repository.save(curso);

            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();
    }
}