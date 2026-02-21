package com.alurachallenge.literAlura;

import com.alurachallenge.literAlura.Principal.Principal;
import com.alurachallenge.literAlura.Repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    @Autowired // Le pedimos a Spring que busque el repositorio
    private LibroRepository repository;


    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Ahora sí, le pasamos el 'repository' que Spring nos dio
        Principal principal = new Principal(repository);
        principal.muestraElMenu();
    }
}