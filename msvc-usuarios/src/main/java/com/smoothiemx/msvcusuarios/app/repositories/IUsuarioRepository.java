package com.smoothiemx.msvcusuarios.app.repositories;

import com.smoothiemx.msvcusuarios.app.models.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioRepository extends CrudRepository<Usuario, Long> {


}