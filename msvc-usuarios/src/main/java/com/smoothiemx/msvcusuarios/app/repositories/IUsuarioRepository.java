package com.smoothiemx.msvcusuarios.app.repositories;

import com.smoothiemx.msvcusuarios.app.models.entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    @Query("select u.email from Usuario u where u.email = ?1")
    Optional<Usuario> porEmail(String email);

    boolean existsByEmail(String email);


}