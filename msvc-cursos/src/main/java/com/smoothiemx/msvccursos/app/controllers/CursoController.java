package com.smoothiemx.msvccursos.app.controllers;

import com.smoothiemx.msvccursos.app.models.entities.Curso;
import com.smoothiemx.msvccursos.app.services.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Optional<Curso> cursoOptional = this.service.porId(id);

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
}