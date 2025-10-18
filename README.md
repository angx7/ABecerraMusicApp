
# 🎵 ABecerraMusicApp

ABecerraMusicApp es una aplicación desarrollada en **Kotlin** utilizando **Jetpack Compose**, que permite visualizar una lista de álbumes musicales y consultar el detalle de cada uno mediante el consumo de una API REST.

---

## 🚀 Tecnologías utilizadas

- **Jetpack Compose** – Interfaz moderna, declarativa y responsiva.
- **Navigation Compose** – Navegación con rutas serializables.
- **ViewModel & State Management** – Manejo de estado con `mutableStateOf` y `LaunchedEffect`.
- **Retrofit** – Consumo de API REST.
- **Coil** – Carga eficiente de imágenes.
- **Material 3** – Componentes actualizados de diseño.
- **Kotlin Coroutines** – Manejo asíncrono y seguro de peticiones.

---

## 🌐 API consumida

| Endpoint | Descripción |
|-----------|--------------|
| `https://music.juanfrausto.com/api/albums` | Lista de álbumes |
| `https://music.juanfrausto.com/api/albums/{id}` | Detalle de un álbum específico |

---

## 🧱 Arquitectura

```
com.example.abecerramusicapp
│
├── data/                  # Modelos y servicios de red (Retrofit)
├── viewmodel/             # ViewModels para manejar el estado UI
├── screens/               # Pantallas principales (List y Detail)
└── components/            # Componentes reutilizables (cards, chips, mini player, etc.)
```

---

## 🎨 Paleta de colores

| Nombre | Color |
|--------|--------|
| DarkBlue | `#0F1B3D` |
| LightBlue | `#1E2E61` |
| AccentBlue | `#5D9CEC` |
| SurfaceLavender | `#F3F0FF` |

---

## 🖋 Tipografía

La aplicación utiliza una tipografía personalizada que refuerza la jerarquía visual, con pesos **bold** en títulos y **regular** en descripciones.

---

## 🧭 Navegación

- **Pantalla principal (AlbumListScreen):** muestra los álbumes disponibles con su imagen, título y artista.
- **Pantalla de detalle (AlbumDetailScreen):** muestra información extendida, imagen hero, descripción, chip de artista y mini player inferior.

---

## 🧹 Mejores prácticas implementadas

- Separación modular de componentes reutilizables.
- Limpieza de importaciones innecesarias.
- Código formateado y documentado para facilitar su lectura y mantenimiento.
- Uso de `SubcomposeAsyncImage` para placeholders mientras carga la imagen.

---

## ✅ Estado final del proyecto

Proyecto **completo y estable**, con diseño moderno, estructura modular y navegación funcional.

Desarrollado por **Angel Becerra (ANGX7)** ❤️
