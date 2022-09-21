package com.driagon.springcloud.usuarios.repositories;

import com.driagon.springcloud.usuarios.models.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {


}