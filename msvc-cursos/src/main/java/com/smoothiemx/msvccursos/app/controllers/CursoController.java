package com.smoothiemx.msvccursos.app.controllers;

import com.smoothiemx.msvccursos.app.models.Usuario;
import com.smoothiemx.msvccursos.app.models.entities.Curso;
import com.smoothiemx.msvccursos.app.services.ICursoService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class CursoController {

    @Autowired
    private ICursoService service;

    @GetMapping("/")
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(this.service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable("id") Long id) {
        Optional<Curso> cursoOptional = this.service.porIdConUsuarios(id);

        if (cursoOptional.isPresent()) {
            return ResponseEntity.ok(cursoOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso , BindingResult result) {

        if (result.hasErrors()) {
            return validar(result);
        }

        Curso cursoDb = this.service.guardar(curso);

        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable("id") Long id) {

        if (result.hasErrors()) {
            return validar(result);
        }

        Optional<Curso> cursoOptional = this.service.porId(id);

        if (cursoOptional.isPresent()) {
            Curso cursoDb = cursoOptional.get();

            cursoDb.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.guardar(cursoDb));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Long id) {
        Optional<Curso> cursoOptional = this.service.porId(id);

        if (cursoOptional.isPresent()) {
            this.service.eliminar(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errores.put(error.getField(), String.format("El campo %s %s", error.getField(), error.getDefaultMessage()));
        });

        return ResponseEntity.badRequest().body(errores);
    }

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable("cursoId") Long cursoId) {
        Optional<Usuario> o;

        try {
            o = this.service.asignarUsuario(usuario, cursoId);
        } catch (FeignException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "No existe usuario por id o error en la comunicación: " + ex.getMessage()));
        }

        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> creaUsuario(@RequestBody Usuario usuario, @PathVariable("cursoId") Long cursoId) {
        Optional<Usuario> o;

        try {
            o = this.service.crearUsuario(usuario, cursoId);
        } catch (FeignException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "No se pudo crear el usuario o error en la comunicación: " + ex.getMessage()));
        }

        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable("cursoId") Long cursoId) {
        Optional<Usuario> o;

        try {
            o = this.service.eliminarUsuario(usuario, cursoId);
        } catch (FeignException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "No existe usuario por id o error en la comunicación: " + ex.getMessage()));
        }

        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable("id") Long id) {
        this.service.eliminarCursoUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }
}