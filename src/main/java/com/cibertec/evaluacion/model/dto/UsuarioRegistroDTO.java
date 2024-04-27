package com.cibertec.evaluacion.model.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioRegistroDTO {
    private Integer idusuario;
    private String nombres;
    private String apellidos;
    private String email;
    private String nomusuario;
    private String password;
}
