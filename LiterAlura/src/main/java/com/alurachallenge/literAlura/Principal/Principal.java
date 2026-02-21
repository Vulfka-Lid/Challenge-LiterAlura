package com.alurachallenge.literAlura.Principal;

import com.alurachallenge.literAlura.Repository.LibroRepository;

import java.util.Scanner;

public class Principal {

    private LibroRepository repositorio;

    // Usamos un constructor para recibir el repositorio desde la clase Application
    public Principal(LibroRepository repository) {
        this.repositorio = repository;
    }

    private Scanner teclado;


    public void muestraElMenu() {
        Scanner lectura = new Scanner(System.in); // <-- El "freno" del programa
        var opcion = -1;

        while (opcion != 0) {
            var menu = """
                    -----------------------------------
                    Elija la opción a través de su número:
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    0 - Salir
                    -----------------------------------
                    """;
            System.out.println(menu);

            // El programa se DETIENE aquí hasta que presiones Enter
            opcion = lectura.nextInt();
            lectura.nextLine(); // Limpiamos el buffer

            switch (opcion) {
                case 1:
                    //buscarLibroWeb();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }
}