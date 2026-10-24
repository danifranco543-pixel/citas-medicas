# Citas médicas — Taller de pruebas Spring Boot

Proyecto académico para practicar pruebas de software con Java 25, Spring Boot 4, Maven, JUnit 5, Mockito, MockMvc, JPA/H2, Selenium, JaCoCo y GitHub Actions.

## Reglas de negocio
- Un profesional no puede tener dos citas en la misma fecha y hora.
- Cada profesional admite máximo 20 citas por día.
- `POST /api/citas`: `201 Created` si se crea; `409 Conflict` si el horario/cupo está ocupado; `400 Bad Request` si hay datos inválidos.
- La interfaz web está en `/citas.html`.

## Requisitos
- JDK 25 e IntelliJ IDEA con soporte para Java 25.
- Maven (o Maven Wrapper si se agrega desde IntelliJ).
- Google Chrome para la prueba de interfaz Selenium.

## Ejecutar
1. Abrir esta carpeta en IntelliJ con **File > Open**.
2. Esperar que Maven sincronice dependencias y configurar Project SDK en Java 25.
3. Ejecutar `co.edu.comfenalco.citas.CitasApplication`.
4. Abrir `http://localhost:8080/citas.html`.

## Ejecutar pruebas
Desde la terminal ubicada en la raíz:
```bash
mvn test
```
La prueba Selenium está etiquetada `ui` y se excluye de la ejecución general para poder correrla por separado. Para ejecutarla:
```bash
mvn -Dgroups=ui -DexcludedGroups= test
```
Si la configuración de Surefire de tu entorno no permite sobrescribir el grupo excluido, ejecuta `ReservaUITest` con el botón Run de IntelliJ.

## Cobertura JaCoCo
```bash
mvn verify
```
Reporte HTML: `target/site/jacoco/index.html`. El build verifica un mínimo del 70 % de cobertura de líneas; si no se alcanza, Maven puede finalizar con error.

## GitHub Actions
El flujo `.github/workflows/ci.yml` ejecuta `mvn -B verify` en cada push y pull request y publica el reporte como artefacto.

## Entregables del taller
- `documentacion/matriz_casos_prueba.md`: matriz CP-01 a CP-08.
- Código de aplicación y pruebas en `src/`.
- Evidencia real de ejecución: capturas de IntelliJ, reporte JaCoCo y ejecución de GitHub Actions. Las capturas deben tomarse después de ejecutar el proyecto en tu equipo.
- `documentacion/registro_defectos.md`: plantilla para registrar defectos encontrados.
