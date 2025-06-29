package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;

    public boolean existeLibro(String titulo, String nombreAutor, String apellidoAutor) {
        return libroRepository.existsByTituloIgnoreCaseAndAutor_NombreIgnoreCaseAndAutor_ApellidoIgnoreCase(
                titulo.trim(), nombreAutor.trim(), apellidoAutor.trim());
    }


    public Libro guardarLibro(Libro libro) {
        // Buscar o guardar autor
        Autor autor = libro.getAutor();
        List<Autor> autores = autorRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(autor.getNombre(), autor.getApellido());
        if (!autores.isEmpty()) {
            libro.setAutor(autores.get(0));
        } else {
            autorRepository.save(autor);
            libro.setAutor(autor);
        }
        return libroRepository.save(libro);
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public List<Libro> listarLibrosPorIdioma(String idioma) {
        return libroRepository.findByIdioma(idioma);
    }

    public List<Libro> mostrarTop10LibrosMasDescargados() {
        return libroRepository.findTop10ByOrderByDescargasDesc();
    }
}
