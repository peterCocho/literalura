package com.aluracursos.literalura.service;

import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.Libro;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class GutendexService {
    private static final String API_URL = "https://gutendex.com/books/";
    private final RestTemplate restTemplate = new RestTemplate();

    public Optional<Libro> buscarLibroPorTitulo(String titulo) {
        try {
            String tituloCodificado = URLEncoder.encode(titulo.trim(), StandardCharsets.UTF_8);
            String url = API_URL + "?search=" + tituloCodificado;

            Map<String, Object> respuesta = restTemplate.getForObject(url, Map.class);
            List<Map<String, Object>> results = (List<Map<String, Object>>) respuesta.get("results");
            if (results == null || results.isEmpty()) return Optional.empty();

            Map<String, Object> primerLibro = results.get(0);
            String tituloLibro = (String) primerLibro.get("title");
            List<String> idiomas = (List<String>) primerLibro.get("languages");
            String idioma = idiomas != null && !idiomas.isEmpty() ? idiomas.get(0) : "desconocido";
            int descargas = primerLibro.get("download_count") != null ? (int) primerLibro.get("download_count") : 0;

            List<Map<String, Object>> autores = (List<Map<String, Object>>) primerLibro.get("authors");
            if (autores == null || autores.isEmpty()) return Optional.empty();

            Map<String, Object> autorMap = autores.get(0);
            String nombreCompleto = (String) autorMap.get("name");
            String[] partes = nombreCompleto.split(", ");
            String apellido = partes.length > 0 ? partes[0] : "";
            String nombre = partes.length > 1 ? partes[1] : "";
            Integer nacimiento = autorMap.get("birth_year") != null ? (Integer) autorMap.get("birth_year") : null;
            Integer fallecimiento = autorMap.get("death_year") != null ? (Integer) autorMap.get("death_year") : null;

            Autor autor = new Autor(nombre, apellido, nacimiento, fallecimiento);
            Libro libro = new Libro(tituloLibro, idioma, descargas, autor);
            return Optional.of(libro);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Libro> obtenerTop10LibrosMasDescargados() {
        try {
            String url = "https://gutendex.com/books/?sort=-download_count&page_size=10";
            Map<String, Object> respuesta = restTemplate.getForObject(url, Map.class);
            List<Map<String, Object>> results = (List<Map<String, Object>>) respuesta.get("results");
            List<Libro> libros = new ArrayList<>();
            if (results == null) return libros;

            // Solo procesa los primeros 10 resultados
            for (int i = 0; i < Math.min(10, results.size()); i++) {
                Map<String, Object> libroMap = results.get(i);
                String tituloLibro = (String) libroMap.get("title");
                List<String> idiomas = (List<String>) libroMap.get("languages");
                String idioma = idiomas != null && !idiomas.isEmpty() ? idiomas.get(0) : "desconocido";
                int descargas = libroMap.get("download_count") != null ? (int) libroMap.get("download_count") : 0;

                List<Map<String, Object>> autores = (List<Map<String, Object>>) libroMap.get("authors");
                String nombre = "", apellido = "";
                Integer nacimiento = null, fallecimiento = null;
                if (autores != null && !autores.isEmpty()) {
                    Map<String, Object> autorMap = autores.get(0);
                    String nombreCompleto = (String) autorMap.get("name");
                    String[] partes = nombreCompleto.split(", ");
                    apellido = partes.length > 0 ? partes[0] : "";
                    nombre = partes.length > 1 ? partes[1] : "";
                    nacimiento = autorMap.get("birth_year") != null ? (Integer) autorMap.get("birth_year") : null;
                    fallecimiento = autorMap.get("death_year") != null ? (Integer) autorMap.get("death_year") : null;
                }
                Autor autor = new Autor(nombre, apellido, nacimiento, fallecimiento);
                libros.add(new Libro(tituloLibro, idioma, descargas, autor));
            }
            return libros;
        } catch (Exception e) {
            return List.of();
        }
    }
}


