package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String nombre, String apellido);
    List<Autor> findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(Integer anio, Integer anio2);
    List<Autor> findByAnioFallecimientoIsNullOrAnioFallecimientoGreaterThan(Integer anio);
}
