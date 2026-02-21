package com.alurachallenge.literAlura.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private Integer AnoDeNacimiento;
    private Integer AnoDeMuerte;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnoDeNacimiento() {
        return AnoDeNacimiento;
    }

    public void setAnoDeNacimiento(Integer anoDeNacimiento) {
        AnoDeNacimiento = anoDeNacimiento;
    }

    public Integer getAnoDeMuerte() {
        return AnoDeMuerte;
    }

    public void setAnoDeMuerte(Integer anoDeMuerte) {
        AnoDeMuerte = anoDeMuerte;
    }
}
