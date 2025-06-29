package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    List<Libro> findByIdioma(String idioma);
    boolean existsByTituloIgnoreCase(String titulo);
    List<Libro> findTop10ByOrderByDescargasDesc();
    boolean existsByTituloIgnoreCaseAndAutor_NombreIgnoreCaseAndAutor_ApellidoIgnoreCase(String titulo, String nombre, String apellido);
}
