package com.alurachallenge.literAlura.Principal;

import com.alurachallenge.literAlura.Model.Autores;
import com.alurachallenge.literAlura.Model.DatosRespuesta;
import com.alurachallenge.literAlura.Model.Libro;
import com.alurachallenge.literAlura.Model.LibroDTO;
import com.alurachallenge.literAlura.Repository.AutoresRepository;
import com.alurachallenge.literAlura.Repository.LibroRepository;
import com.alurachallenge.literAlura.Service.ConsumoApi;
import com.alurachallenge.literAlura.Service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);

    private LibroRepository repositorio;
    private AutoresRepository autoresRepository;
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();

    // Usamos un constructor para recibir el repositorio desde la clase Application
    public Principal(LibroRepository repository, AutoresRepository autoresRepository) {
        this.repositorio = repository;
        this.autoresRepository = autoresRepository;
    }


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
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libro por idioma
                    0 - Salir
                    -----------------------------------
                    """;
            System.out.println(menu);

            // El programa se DETIENE aquí hasta que presiones Enter
            opcion = lectura.nextInt();
            lectura.nextLine(); // Limpiamos el buffer

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;

                case 2:
                    listarLibrosRegistrados();
                    break;

                case 3:
                    listarAutoresRegistrados();
                    break;

                case 4:
                    listarAutoresVivosEnAnio();
                    break;

                case 5:
                    listarLibrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }



        }

    }

    private void buscarLibroWeb() {
        System.out.println("Por favor, escribe el nombre del libro que deseas buscar:");
        Scanner teclado = new Scanner(System.in);
        var nombreLibro = teclado.nextLine(); // o lectura.nextLine()

        // 1. Construir la URL
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "%20"));

        // 2. Convertir el JSON usando el nuevo record DatosRespuesta
        var datosBusqueda = conversor.obtenerDatos(json, DatosRespuesta.class);

        // 3. Buscar el libro dentro de la lista de resultados
        Optional<LibroDTO> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();

        // 4. Guardar si se encontró
        if (libroBuscado.isPresent()) {
            System.out.println("¡Libro encontrado!");
            Libro libro = new Libro(libroBuscado.get()); // Tu entidad con el constructor que hicimos
            boolean yaExiste = repositorio.existsByTitulo(libro.getTitulo());

            if (yaExiste) {
                System.out.println("¡Aviso! Este libro ya está registrado en tu base de datos.");
            } else {
                repositorio.save(libro);
                System.out.println(libro);
            }
        } else {
            System.out.println("Libro no encontrado.");
        }
    }

    private void listarLibrosRegistrados() {
        // Traemos todos los libros desde la base de datos de litio/Postgres
        List<Libro> libros = repositorio.findAll();

        // Los imprimimos todos usando el toString() que acabamos de crear
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo)) // Opcional: ordenarlos por título
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        List<Libro> libros = repositorio.findAll();
        System.out.println("----- AUTORES EN TU COLECCIÓN -----");
        libros.stream()
                .map(Libro::getAutores)
                .distinct() // Para no repetir nombres si tienes varios libros del mismo autor
                .forEach(System.out::println);
    }

    private void listarAutoresVivosEnAnio() {
        System.out.println("Ingresa el año que deseas consultar:");
        var anio = teclado.nextInt();
        teclado.nextLine(); // Limpiar el buffer

        List<Autores> autores = autoresRepository.buscarAutoresVivosEnAnio(anio);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores registrados vivos en el año " + anio);
        } else {
            autores.forEach(a -> System.out.println(
                    "Autor: " + a.getNombre() +
                            " | Nacimiento: " + a.getFechaNacimiento() +
                            " | Fallecimiento: " + (a.getFechaNacimiento() == null ? "N/A" : a.getFechaFallecimiento())
            ));
        }
    }

    private void listarLibrosPorIdioma() {
        var menuIdiomas = """
            Ingrese el idioma para buscar los libros:
            es - Español
            en - Inglés
            fr - Francés
            pt - Portugués
            """;
        System.out.println(menuIdiomas);
        var idiomaElegido = teclado.nextLine().toLowerCase(); // Lo pasamos a minúscula por seguridad

        List<Libro> librosPorIdioma = repositorio.findByIdiomasContains(idiomaElegido);

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma seleccionado en la base de datos.");
        } else {
            System.out.println("----- LIBROS EN " + idiomaElegido.toUpperCase() + " -----");
            librosPorIdioma.forEach(System.out::println);
        }
    }
}