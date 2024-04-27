package com.cibertec.evaluacion.repository;

import com.cibertec.evaluacion.model.bd.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.query.Param;

@Repository
public interface UsuarioRepository extends
        JpaRepository<Usuario, Integer> {
    Usuario findByNomusuario(String nomusuario);

    @Transactional
    @Modifying
    @Query(value = "UPDATE usuario SET nombres=:nombres, apellidos=:apellidos, " +
            "activo=:activo WHERE idusuario=:idusuario",
            nativeQuery = true)
    void actualizarUsuario(@Param("nombres")String nombres,
                           @Param("apellidos")String apellidos,
                           @Param("activo")boolean activo,
                           @Param("idusuario")int idusuario);


    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.nomusuario = ?1")
    boolean existsByNomusuario(String nomusuario);
}