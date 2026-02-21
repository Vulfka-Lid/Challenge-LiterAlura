package com.alurachallenge.literAlura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record autoresDTO(

        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer AnoDeNacimiento,
        @JsonAlias("death_year") Integer AnoDeMuerte
) {
}
