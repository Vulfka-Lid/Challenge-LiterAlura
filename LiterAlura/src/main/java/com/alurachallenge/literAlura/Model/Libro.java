package com.alurachallenge.literAlura.Model;

import jakarta.persistence.*;

import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String autores;
    private String idiomas;
    private Integer descargas;

    public Libro() {} // Constructor vacío obligatorio para JPA

    public Libro(LibroDTO datosLibro) {
        this.titulo = datosLibro.titulo();
        this.descargas = datosLibro.descargas();

        // Convertimos la lista de idiomas ["en", "es"] a un String "en, es"
        this.idiomas = String.join(", ", datosLibro.idiomas());

        // Para los autores, como es una lista de objetos (DatosAutor),
        // primero extraemos los nombres y luego los unimos
        this.autores = datosLibro.autores().stream()
                .map(a -> a.nombre())
                .collect(Collectors.joining(", "));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return "----------------------------" +
                "\nLibro: " + titulo +
                "\nAutor: " + autores +
                "\nIdioma: " + idiomas +
                "\nNúmero de descargas: " + descargas +
                "\n----------------------------";
    }
}
