package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> buscarAutoresVivosEnAnio(int anio) {
        return autorRepository.findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(anio, anio);
    }

    public List<Autor> buscarAutoresPorNombre(String nombre) {
        return autorRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, nombre);
    }
}
