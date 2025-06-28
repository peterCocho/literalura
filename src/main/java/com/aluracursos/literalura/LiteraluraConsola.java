package com.aluracursos.literalura;

import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.service.GutendexService;
import com.aluracursos.literalura.service.LibroService;
import com.aluracursos.literalura.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class LiteraluraConsola implements CommandLineRunner {
    @Autowired
    private GutendexService gutendexService;
    @Autowired
    private LibroService libroService;
    @Autowired
    private AutorService autorService;
    @Autowired
    private ApplicationContext context;

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n--- Catálogo LiterAlura ---");
            System.out.println("1. Buscar libro por título y registrar");
            System.out.println("2. Listar libros registrados");
            System.out.println("3. Listar autores registrados");
            System.out.println("4. Listar autores vivos en un año");
            System.out.println("5. Listar libros por idioma");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Por favor ingrese un número válido: ");
                scanner.next();
            }

            opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            switch (opcion) {
                case 1 -> buscarYRegistrarLibro(scanner);
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivos(scanner);
                case 5 -> listarLibrosPorIdioma(scanner);
                case 0 -> {
                    System.out.println("¡Hasta luego!");
                    SpringApplication.exit(context);
                    return;
                }
                default -> System.out.println("Opción inválida");
            }
        } while (true); // Salida controlada por el return del case 0
    }

    private void buscarYRegistrarLibro(Scanner scanner) {
        System.out.print("Ingrese el título del libro: ");
        String titulo = scanner.nextLine();
        if (libroService.existeLibro(titulo)) {
            System.out.println("El libro ya está registrado en la base de datos.");
            return;
        }
        Optional<Libro> libroOpt = gutendexService.buscarLibroPorTitulo(titulo);
        if (libroOpt.isEmpty()) {
            System.out.println("El libro no fue encontrado en la API de Gutendex.");
            return;
        }
        Libro libro = libroOpt.get();
        libroService.guardarLibro(libro);
        System.out.println("Libro registrado exitosamente: " + libro.getTitulo());
    }

    private void listarLibros() {
        List<Libro> libros = libroService.listarLibros();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        System.out.println("\nLibros registrados:");
        libros.forEach(l -> System.out.println(l.getTitulo() + " | " + l.getAutor().getApellido() + ", " + l.getAutor().getNombre() + " | Idioma: " + l.getIdioma() + " | Descargas: " + l.getDescargas()));
    }

    private void listarAutores() {
        List<Autor> autores = autorService.listarAutores();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }
        System.out.println("\nAutores registrados:");
        autores.forEach(a -> System.out.println(a.getApellido() + ", " + a.getNombre() + " | Nacimiento: " + a.getAnioNacimiento() + " | Fallecimiento: " + a.getAnioFallecimiento()));
    }

    private void listarAutoresVivos(Scanner scanner) {
        System.out.print("Ingrese el año: ");
        int anio = scanner.nextInt();
        scanner.nextLine();
        List<Autor> autores = autorService.buscarAutoresVivosEnAnio(anio);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
            return;
        }
        System.out.println("\nAutores vivos en el año " + anio + ":");
        autores.forEach(a -> System.out.println(a.getApellido() + ", " + a.getNombre() + " | Nacimiento: " + a.getAnioNacimiento() + " | Fallecimiento: " + a.getAnioFallecimiento()));
    }

    private void listarLibrosPorIdioma(Scanner scanner) {
        System.out.print("Ingrese el código de idioma (ej: en, es, fr): ");
        String idioma = scanner.nextLine();
        List<Libro> libros = libroService.listarLibrosPorIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma.");
            return;
        }
        System.out.println("\nLibros en idioma " + idioma + ":");
        libros.forEach(l -> System.out.println(l.getTitulo() + " | " + l.getAutor().getApellido() + ", " + l.getAutor().getNombre()));
    }
}
