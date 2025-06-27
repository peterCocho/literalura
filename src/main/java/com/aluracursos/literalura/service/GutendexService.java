package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.Libro;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.*;

@Service
public class GutendexService {
    private static final String API_URL = "https://gutendex.com/books/";
    private final RestTemplate restTemplate = new RestTemplate();

    public Optional<Libro> buscarLibroPorTitulo(String titulo) {
        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("search", titulo)
                .toUriString();
        Map response = restTemplate.getForObject(url, Map.class);
        List results = (List) response.get("results");
        if (results == null || results.isEmpty()) {
            return Optional.empty();
        }
        Map primerLibro = (Map) results.get(0);
        String tituloLibro = (String) primerLibro.get("title");
        String idioma = ((List<String>) primerLibro.get("languages")).get(0);
        int descargas = (int) primerLibro.get("download_count");
        List autores = (List) primerLibro.get("authors");
        if (autores.isEmpty()) return Optional.empty();
        Map autorMap = (Map) autores.get(0);
        String nombreCompleto = (String) autorMap.get("name");
        String[] partes = nombreCompleto.split(", ");
        String apellido = partes.length > 0 ? partes[0] : "";
        String nombre = partes.length > 1 ? partes[1] : "";
        Integer nacimiento = autorMap.get("birth_year") != null ? (Integer) autorMap.get("birth_year") : null;
        Integer fallecimiento = autorMap.get("death_year") != null ? (Integer) autorMap.get("death_year") : null;
        Autor autor = new Autor(nombre, apellido, nacimiento, fallecimiento);
        Libro libro = new Libro(tituloLibro, idioma, descargas, autor);
        return Optional.of(libro);
    }
}
