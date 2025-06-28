# 📚 LiterAlura

LiteralAlura es una aplicación Java con Spring Boot que permite consultar, registrar y listar libros y autores obtenidos desde la API pública de [Gutendex](https://gutendex.com/). 

Esta app trabaja con persistencia en base de datos e interfaz por consola, ideal para practicar consumo de API REST, programación orientada a objetos, persistencia con JPA y uso de Spring Boot.

---

## 🚀 Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **H2 Database (modo desarrollo)**
- **Lombok**
- **Gutendex API (fuente de libros y autores)**
- **Maven** para gestión de dependencias

---

## ⚙️ ¿Cómo ejecutar el proyecto?

1. **Clonar el repositorio:**

   ```bash
   git clone https://github.com/tu_usuario/literalura.git
   cd literalura
2. **Construir el proyecto con Maven:**

   mvn clean install

3. **Ejecutar la aplicación:**
   
    mvn spring-boot:run


4. **¡Listo! Interactúa con la consola:**

--- Catálogo LiterAlura ---
  1. Buscar libro por título y registrar
  2. Listar libros registrados
  3. Listar autores registrados
  4. Listar autores vivos en un año
  5. Listar libros por idioma
  6. Top 10 libros mas descargados
  0. Salir

   
## 🧩 Funcionalidades

- 🔍 **Buscar libro**: por título, usando la API de Gutendex.
- 📚 **Listar libros**: mostrar libros guardados, idioma y autor.
- 🖋️ **Listar autores**: muestra todos los autores únicos registrados.
- 📅 **Autores vivos por año**: filtra autores que vivían en un año dado.
- 🌍 **Libros por idioma**: filtra libros según el código de idioma.
- ❌ **Salir de la app**: permite cerrar correctamente la ejecución.

Estructura del proyecto

## 📁 Estructura del proyecto

```text
literalalura/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/aluracursos/literalura/
│   │   │       ├── LiteraluraConsola.java         # Interfaz por consola (CommandLineRunner)
│   │   │       ├── model/
│   │   │       │   ├── Libro.java
│   │   │       │   └── Autor.java
│   │   │       ├── repository/
│   │   │       │   ├── LibroRepository.java
│   │   │       │   └── AutorRepository.java
│   │   │       └── service/
│   │   │           ├── GutendexService.java        # Conexión con la API externa
│   │   │           ├── LibroService.java
│   │   │           └── AutorService.java
│   └── resources/
│       ├── application.properties                  # Configuración de BD (H2, puerto, etc.)
│       ├── data.sql                                # Datos precargados (opcional)
│       └── schema.sql                              # Esquema de la BD (opcional)
├── pom.xml
└── README.md

     ┌────────────────────┐
     │ LiteraluraConsola  │
     └────────┬───────────┘
              │
      ┌───────▼────────┐
      │ GutendexService│
      └───────┬────────┘
              ▼
     ┌─────────────────────┐
     │ Consumo de API REST │
     └────────┬────────────┘
              ▼
 ┌────────────▼────────────┐
 │      LibroService       │
 └────────────┬────────────┘
              ▼
     ┌────────▼────────┐
     │ LibroRepository │
     └────────┬────────┘
              ▼
         ┌────▼────┐
         │ Libro   │
         └─────────┘

 ┌────────────▼────────────┐
 │      AutorService       │
 └────────────┬────────────┘
              ▼
     ┌────────▼────────┐
     │ AutorRepository │
     └────────┬────────┘
              ▼
         ┌────▼────┐
         │ Autor   │
         └─────────┘

```

## 📌 Notas importantes

- Usa `scanner.nextLine()` después de `scanner.nextInt()` para evitar errores de salto de línea.
- Puedes personalizar el puerto desde `application.properties` con:
  ```properties
  server.port=8081


## 📝 Licencia

Este proyecto está licenciado bajo los términos de la licencia [MIT](https://opensource.org/licenses/MIT).

---

## ✍️ Autor

**Pedro Pablo Contreras Vega**  
Proyecto desarrollado como parte del curso de **Java y Spring Boot** — *Alura Latam + Oracle Next Education*

