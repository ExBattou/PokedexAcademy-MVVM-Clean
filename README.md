# 🔴 PokeDex Academy: Clean Architecture & Observers Masterclass

Aplicación Android nativa desarrollada en **Kotlin** con **Jetpack Compose**, **Clean Architecture**, **MVVM** e inyección de dependencias con **Hilt**. 

Diseñada específicamente con fines pedagógicos para enseñar y comparar el patrón **Observer** utilizando **LiveData** frente a **Kotlin StateFlow / SharedFlow**, además de un módulo taller para alumnos.

---

## 🏛️ Arquitectura del Proyecto

El proyecto sigue una separación rigurosa de responsabilidades en tres capas limpias:

```
app/src/main/java/com/pokedex/academy/
 ├── core/                          # Modelos comunes, UiState, Tema Compose y Rutas
 ├── data/
 │    ├── remote/                   # PokeApiService (Retrofit) y DTOs
 │    ├── mapper/                   # Mappers DTO -> Domain Entity
 │    └── repository/               # Implementación del Repositorio (IO Dispatcher)
 ├── domain/                        # KOTLIN PURO (Sin dependencias de Android SDK)
 │    ├── model/                    # Pokemon y PokemonDetail
 │    ├── repository/               # Interfaz del Repositorio (DIP)
 │    └── usecase/                  # GetGen1, GetGen2, GetGen3 y GetDetail UseCases
 ├── presentation/
 │    ├── common/components/        # Componentes UI reutilizables (Cards, Stats, Banners)
 │    ├── gen1_livedata/            # MÓDULO 1: Lista y Detalle con LiveData
 │    ├── gen2_flow/                # MÓDULO 2: Lista y Detalle con StateFlow + SharedFlow
 │    └── exercise/                 # MÓDULO 3: Taller interactivo para alumnos (Gen 3)
 └── di/                            # Módulos de Inyección de Dependencias Hilt
```

---

## 📱 Módulos de la Aplicación

1. **📕 Gen 1 - Kanto (LiveData):**
   * Implementado con `MutableLiveData` privado y `LiveData` público.
   * Consumido en Jetpack Compose mediante la función de extensión `observeAsState()`.
   * Detalle completo del Pokémon utilizando LiveData.

2. **⚡ Gen 2 - Johto (StateFlow + SharedFlow):**
   * **Estado de UI:** Manejado con `StateFlow` y consumido con `collectAsStateWithLifecycle()` (eficiente para el ciclo de vida de Android).
   * **Eventos One-Shot:** Manejados con `SharedFlow` para notificaciones efímeras (Snackbars y acciones de favoritos) sin re-disparos al girar la pantalla.
   * Detalle completo del Pokémon con botón de favoritos reactivo.

3. **🎓 Módulo Taller / Ejercicio Alumnos (Gen 3 - Hoenn):**
   * La capa de red, mapeadores, repositorio y UseCase (`GetGen3PokemonUseCase`) ya están implementados y conectados.
   * Los archivos `ExerciseViewModel.kt` y `ExerciseDetailViewModel.kt` contienen pasos guiados `// TODO [ALUMNO 1..N]` para que los estudiantes practiquen la implementación reactiva por sí mismos.

---

## 🚀 Requisitos y Cómo Ejecutar el Proyecto

* **Android Studio:** Ladybug, Koala o posterior.
* **JDK:** 17 o superior.
* **Min SDK:** 24 (Android 7.0) | **Target SDK:** 35.
* **API Utilizada:** [PokeAPI](https://pokeapi.co/) (No requiere API Key).

1. Abre Android Studio y selecciona **Open...** en la carpeta del proyecto.
2. Espera a que Gradle sincronice las dependencias.
3. Ejecuta la aplicación en un emulador o dispositivo físico con conexión a internet.
