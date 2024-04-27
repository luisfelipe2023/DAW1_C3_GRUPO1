package com.cibertec.evaluacion.model.bd;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="rol")
public class Rol {
    // Identificador único del usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idrol;
    private String nomrol;

    public Rol() {

    }


    public Integer getIdrol() {
        return idrol;
    }

    public void setIdrol(Integer idrol) {
        this.idrol = idrol;
    }

    public String getNomrol() {
        return nomrol;
    }

    public void setNomrol(String nomrol) {
        this.nomrol = nomrol;
    }


    public Rol(String nomrol, Integer idrol) {
        this.nomrol = nomrol;
        this.idrol = idrol;
    }


    public Rol(String nomrol) {
        this.nomrol = nomrol;
    }
}