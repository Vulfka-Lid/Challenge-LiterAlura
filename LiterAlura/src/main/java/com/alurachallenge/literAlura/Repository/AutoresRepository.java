package com.alurachallenge.literAlura.Repository;

import com.alurachallenge.literAlura.Model.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoresRepository extends JpaRepository<Autores, Long> {
    @Query("SELECT a FROM Autores a WHERE a.fechaNacimiento <= :anio AND (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento >= :anio)")
    List<Autores> buscarAutoresVivosEnAnio(Integer anio);
}
