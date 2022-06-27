package com.smoothiemx.msvcusuarios.app.controllers;

import com.smoothiemx.msvcusuarios.app.models.entities.Usuario;
import com.smoothiemx.msvcusuarios.app.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> crear(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Usuario usuario, @PathVariable("id") Long id) {
        Optional<Usuario> usuarioOptional = this.service.porId(id);

        if (usuarioOptional.isPresent()) {
            Usuario newUsuario = usuarioOptional.get();
            newUsuario.setNombre(usuario.getNombre());
            newUsuario.setEmail(usuario.getEmail());
            newUsuario.setPassword(usuario.getPassword());

            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.guardar(newUsuario));
        }

        return ResponseEntity.notFound().build();
    }
}