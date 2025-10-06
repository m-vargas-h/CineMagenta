# Experiencia 3 – Semana 8
# 🎬 CineMagenta — Prototipo de Gestión de Cartelera Cinematográfica

## 📌 Descripción del proyecto

**CineMagenta** es una aplicación Java con interfaz gráfica que permite gestionar una cartelera de películas almacenadas en una base de datos MySQL. Este prototipo fue desarrollado como parte de una evaluación sumativa, cumpliendo los requisitos de creación de base de datos, interfaz principal y formulario de ingreso, modificación, eliminación y búsqueda de películas.

El proyecto está diseñado con enfoque en modularidad, validación de datos, y experiencia de usuario, y se encuentra en fase de desarrollo incremental.

---

## 🗂️ Estructura del repositorio

```
Proyecto cineMagenta/ 
├── CineMagenta/                            # Proyecto Java creado en NetBeans 
│   ├── src/ 
│   │   ├── dao/                            # PeliculaDAO 
│   │   │   └── PeliculaDAO.java
│   │   ├── model/                          # Clases base
│   │   │   ├── Genero.java
│   │   │   └── Pelicula.java  
│   │   ├── resources/                      # Recursos gráficos para la interfaz
│   │   ├── service/                        # Gestión de operaciones relacionadas con películas
│   │   │   └── PeliculaService.java
│   │   ├── util/                           # ConexionDB y PeliculaValidador 
│   │   │   ├── ConexionDB.java
│   │   │   ├── DialogUtils.java 
│   │   │   ├── PeliculaValidador.java 
│   │   │   └── VentanaManager.java
│   │   ├── view/                           # MainFrame y Formularios
│   │   │   ├── FormularioAgregar.java 
│   │   │   ├── FormularioBase.java 
│   │   │   ├── FormularioBuscar.java 
│   │   │   ├── FormularioEliminar.java 
│   │   │   ├── FormularioListar.java 
│   │   │   ├── FormularioModificar.java 
│   │   │   └── MainFrame.java
│   │   ├── resources/                      # Recursos gráficos para la interfaz
│   │   └── CineMagenta.java
│   ├── test/ 
│   │   ├── service/                        # Test para PeliculaService
│   │   │   └── PeliculaServiceTest.java
│   │   └── util/                           # Teste para PeliculaValidador
│   │       └── PeliculaValidadorTest.java
│   ├── nbproject/                       
│   ├── build.xml                        
│   └── manifest.mf                        
├── docs/                                   # Documentación técnica 
│   ├── Portadas/                           # Contiene las caratulas para películas 
│   ├── Cine_DB.sql        
│   ├── UML_CineMagenta    
│   └── javadoc/ 
├── lib/                                    # Contiene archivos .jar necesarios para el proyecto
└── README.md          
```
---

## 🗄️ Base de datos

- **Nombre**: `Cine_DB`
- **Tabla**: `Cartelera`
- **Campos**:
  - `id` INT, autoincremental
  - `titulo` VARCHAR(150)
  - `director` VARCHAR(50)
  - `anno` INT, NOT NULL
  - `duracion` INT, minutos
  - `genero` VARCHAR(50)
  - `ruta_portada` VARCHAR(255)

### Datos iniciales

El script incluye 7 películas precargadas:
- Harry Potter y la Piedra Filosofal (2001)
- Harry Potter y la Cámara Secreta (2002)
- La La Land (2016)
- El Padrino (1972)
- Jurassic Park (1993)
- Amélie (2001)
- Parasite (2019)

El script de creación se encuentra en [`docs/Cine_DB.sql`](docs/Cine_DB.sql).

---

## 🖥️ Funcionalidades implementadas

- **Interfaz principal (`MainFrame`)** con barra de herramientas

- **Formulario “Agregar película”** con:
  - Validación de campos vacíos
  - Botón “Limpiar”
  - Inserción real en MySQL usando `PeliculaDAO`
  - Cuadros de diálogo para retroalimentación
- **Formulario “Modificar película”** con:
  - Búsqueda por título con carga automática de campos
  - Campos editables: título, director, año, duración, género
  - Visualización de portada y datos clave (director, año, duración, género)
  - Género disponible como lista desplegable
- **Formulario “Eliminar película”** con:
  - Búsqueda por título con confirmación previa
  - Visualización de portada y datos clave (director, año, duración, género)
  - Validación de existencia antes de eliminar
  - Mensajes de éxito o error según el resultado
- **Formulario “Listar películas”** con:
  - Filtros opcionales por género y rango de años
  - Visualización en tabla con datos clave
  - Botón para limpiar filtros y recargar resultados
- **Formulario “Buscar película”** con:
  - Búsqueda parcial por título
  - Resultados dinámicos con visualización de portada y detalles
  - Validación de entrada y retroalimentación clara
- **Validación robusta** con `PeliculaValidador` para asegurar integridad de datos antes de insertar o modificar.
- **Gestión de lógica de negocio** con `PeliculaService`, incluyendo validación, logging y manejo de errores.
- **Mensajes centralizados** con `DialogUtils` para mantener consistencia en la interfaz.
- **Pruebas unitarias** con JUnit 4 para `PeliculaValidador` y `PeliculaService`, asegurando confiabilidad del sistema

---

## 🧪 Pruebas unitarias

El proyecto incluye pruebas automatizadas con JUnit 4 para validar la lógica de negocio y la integridad de datos:

- `PeliculaValidadorTest`: verifica que los datos inválidos sean rechazados correctamente.
- `PeliculaServiceTest`: simula operaciones de inserción, modificación, eliminación y búsqueda, validando el comportamiento esperado.

Las pruebas se ejecutan directamente en NetBeans.

---

## 📐 Diagrama UML

El diagrama UML del sistema se encuentra en la carpeta [`docs/UML_CineMagenta.png`](docs/UML_CineMagenta.png). Este representa las clases principales del modelo, DAO, y flujo de interacción entre la interfaz gráfica y la base de datos.
![Diagrama UML](docs/UML_CineMagenta.png)

---

## 📚 Documentación Javadoc

La documentación técnica generada con Javadoc esta disponible en la carpeta [`docs/javadoc/`](docs/javadoc/). 

---

## ⚙️ Requisitos técnicos

- **Java 8+**
- **MySQL 8.0+**
- **Driver JDBC MySQL** (`mysql-connector-java`)
- **JUnit 4.13.2** — para pruebas unitarias
- **Hamcrest Core 1.3** — requerido por JUnit para aserciones
- **IDE recomendado**: NetBeans, IntelliJ o Eclipse

Si tu entorno no incluye las librerías de prueba automáticamente, puedes agregarlas manualmente desde la carpeta /lib. Esta carpeta contiene los archivos .jar necesarios para ejecutar las pruebas correctamente.

---

## 🚀 Cómo ejecutar

1. Clona o descarga el repositorio.
2. Abre el proyecto `CineMagenta` en NetBeans.
3. Asegúrate de tener MySQL corriendo y ejecuta el script `docs/Cine_DB.sql`.
4. Configura correctamente la clase `ConexionDB` con tus credenciales.
5. Ejecuta `CineMagenta.java` para iniciar la interfaz.

---

## 🧭 Próximos pasos

- Mejorar la experiencia visual y navegación de la interfaz.
- Validar duplicación de `id` si se permite ingreso manual.
- Agregar pruebas de integración para validar el flujo completo entre UI, Service y DAO.
- Extender el uso de Logger a las clases de formulario (`FormularioAgregar`, `FormularioModificar`, `FormularioEliminar`, `FormularioBuscar`, `FormularioListar`) para mejorar trazabilidad y depuración.

---

**Autor**: Miguel Vargas  
**Fecha**: Octubre 2025  
