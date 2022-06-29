package com.smoothiemx.msvcusuarios.app.controllers;

import com.smoothiemx.msvcusuarios.app.models.entities.Usuario;
import com.smoothiemx.msvcusuarios.app.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UsuarioController {

    @Autowired
    private IUsuarioService service;

    @GetMapping("/")
    public List<Usuario> listar() {
        return this.service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable("id") Long id) {
        Optional<Usuario> usuarioOptional = this.service.porId(id);

        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok().body(usuarioOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {

        if (result.hasErrors()) {
            return validar(result);
        }

        if (!usuario.getEmail().isEmpty() && this.service.existePorEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Ya existe un usuario con este correo electrónico"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable("id") Long id) {

        if (result.hasErrors()) {
            return validar(result);
        }

        Optional<Usuario> usuarioOptional = this.service.porId(id);

        if (usuarioOptional.isPresent()) {
            Usuario newUsuario = usuarioOptional.get();

            if (!usuario.getEmail().isEmpty() && !usuario.getEmail().equalsIgnoreCase(newUsuario.getEmail()) && this.service.porEmail(usuario.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Ya existe un usuario con este correo electrónico"));
            }

            newUsuario.setNombre(usuario.getNombre());
            newUsuario.setEmail(usuario.getEmail());
            newUsuario.setPassword(usuario.getPassword());

            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.guardar(newUsuario));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Long id) {
        Optional<Usuario> usuarioOptional = this.service.porId(id);

        if (usuarioOptional.isPresent()) {
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