# CampusGPS 📍

**Estudiante:** Angieluz Vasquez Macalupu

## Descripción

CampusGPS es una aplicación Android desarrollada para el curso de Programación en Móviles con Kotlin, Jetpack Compose y Material 3, organizada mediante la arquitectura MVVM + Repository y el paquete `com.tecsup.campusgps`. La aplicación solicita permiso de ubicación precisa o aproximada, obtiene la posición del dispositivo mediante FusedLocationProviderClient y la muestra en Google Maps con un marcador rojo titulado «Estás aquí». También presenta cinco lugares ficticios del campus, definidos cerca del punto base (-12.0464, -77.0428), en Lima, calcula sus distancias en metros y los ordena del más cercano al más lejano. Al seleccionar una tarjeta, la cámara se desplaza hasta el lugar y la tarjeta queda resaltada. El lugar más cercano se identifica con un marcador verde y puede seleccionarse mediante el botón «Ir al más cercano». Además, la aplicación muestra mensajes y acciones para manejar permisos denegados, ubicación desactivada o no disponible y ausencia de Google Play Services. La clave de Google Maps se configura en `local.properties`, archivo excluido del repositorio.

## Estructura de paquetes

```text
com.tecsup.campusgps/
├── MainActivity.kt
├── model/
│   └── Modelos.kt
├── data/
│   ├── UbicacionRepository.kt
│   ├── UbicacionRepositoryGoogle.kt
│   ├── LugaresRepository.kt
│   └── LugaresRepositoryLocal.kt
├── util/
│   ├── Distancia.kt
│   └── Formato.kt
├── viewmodel/
│   ├── MapaViewModel.kt
│   └── CampusViewModelFactory.kt
└── ui/
    ├── PantallaMapa.kt
    ├── MapaUbicacion.kt
    ├── ListaLugares.kt
    ├── MensajeError.kt
    └── theme/
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt
```

## Diagrama MVVM

El siguiente diagrama usa texto para que pueda visualizarse sin depender de un renderizador de diagramas.

```text
                    VIEW
               PantallaMapa
          ┌──────────┼──────────┐
   MapaUbicacion ListaLugares MensajeError
                    │  ▲
         Acciones   │  │ Estado mediante StateFlow
                    ▼  │
                VIEWMODEL
              MapaViewModel
                    │
          Consulta las interfaces
                    │
          ┌─────────┴─────────┐
          ▼                   ▼
 UbicacionRepository   LugaresRepository
          ▲                   ▲
     Implementa           Implementa
          │                   │
 UbicacionRepositoryGoogle    LugaresRepositoryLocal
          │                   │
          ▼                   ▼
 FusedLocationProviderClient  Cinco lugares ficticios
```

### Responsabilidades

| Capa | Responsabilidad |
|---|---|
| View | Representar el estado y comunicar las acciones del usuario. |
| ViewModel | Gestionar el estado, consultar las interfaces de repositorio y preparar las distancias ordenadas. |
| Repository | Obtener la ubicación o proporcionar los lugares del campus. |
| Model | Representar posiciones, lugares y estados de la aplicación. |
| Util | Calcular distancias y darles formato para mostrarlas. |

El ViewModel no recibe `Context`. Los composables no consultan repositorios ni calculan distancias.

## Capturas de pantalla

| Solicitud de permiso | Mapa con ubicación actual | Marcadores del campus |
|:---:|:---:|:---:|
| <img src="https://github.com/user-attachments/assets/df5e484b-040d-48d8-8136-d3734532c8a4" width="230" alt="Solicitud de permiso" /> | <img src="https://github.com/user-attachments/assets/1da06d26-cad9-496f-9c2d-558718267974" width="230" alt="Mapa con ubicación actual" /> | <img src="https://github.com/user-attachments/assets/a668a075-8726-469b-a292-9176ce05ad30" width="230" alt="Marcadores del campus" /> |
| **Lista ordenada** | **Estado de error** | |
| <img src="https://github.com/user-attachments/assets/d30b99d8-bd75-4659-8f9a-22aef0a2afd3" width="230" alt="Lista ordenada" /> | <img src="https://github.com/user-attachments/assets/49259c54-2d9b-496e-8e99-fdaed47e8b9e" width="230" alt="Estado de error" /> | |

## Pruebas de aceptación

| Prueba | Resultado esperado | OK |
|---|---|:---:|
| Abrir la app por primera vez y pulsar el botón | Aparece el cuadro de permiso de Android. | ☑ |
| Aceptar el permiso | Se obtiene la ubicación y aparece el marcador rojo «Estás aquí». Su título se muestra al tocarlo. | ☑ |
| Negar el permiso | Aparece un mensaje de error con «Abrir ajustes» y la app no se cierra. | ☑ |
| Ubicación en el punto base | Los cinco lugares aparecen cerca de tu posición. El más cercano queda verde y los demás azules tras calcular las distancias. | ☑ |
| Tocar un marcador de lugar | Se muestran el nombre y la categoría del lugar. | ☑ |
| Lista de lugares | Los lugares aparecen ordenados de menor a mayor distancia, con m o km. | ☑ |
| Tocar una tarjeta de la lista | La cámara viaja al lugar y la tarjeta queda resaltada. | ☑ |
| Ir al más cercano | La cámara viaja al lugar verde. El botón está deshabilitado antes de obtener ubicación. | ☑ |
| Cambiar la ubicación del emulador y buscar otra vez | Se actualizan las distancias, el orden de la lista y el lugar verde. | ☑ |
| Ubicación apagada | Aparece «Activa el GPS e intenta de nuevo» y el botón «Reintentar». | ☑ |
| Rotar la pantalla | Se conservan la ubicación obtenida y la lista de lugares. | ☑ |
| Revisar el repositorio | La clave real no aparece en los archivos versionados y `local.properties` no está subido. | ☑ |

## Para pensar

### **1. ¿Por qué FusedLocationProviderClient está dentro del Repository y no en el ViewModel?**

Porque `FusedLocationProviderClient` pertenece a Google Play Services y necesita un contexto de Android para funcionar. Por eso lo coloqué en el Repository, para separar la parte de obtener la ubicación de la parte que maneja los estados de la aplicación. Así, el ViewModel solo trabaja con `UbicacionRepository` y no depende directamente de Google ni necesita recibir un `Context`. Además, esto facilita realizar pruebas o cambiar la implementación más adelante.

### **2. ¿Qué diferencia hay entre el permiso de ubicación y la clave de API de Maps? ¿Qué pasa si falta cada uno?**

El permiso de ubicación sirve para que el usuario autorice a la aplicación a acceder a la ubicación de su dispositivo, mientras que la clave de API sirve para poder utilizar Google Maps. Si no tenemos el permiso, la aplicación no podrá obtener la ubicación del usuario y tendrá que mostrar un mensaje para que lo habilite. En cambio, si falta la clave o está mal configurada, el mapa no cargará, pero eso no impide obtener la ubicación del dispositivo.

### **3. ¿Por qué la clave va en local.properties y no en el código? ¿Qué harías si se subiera a GitHub por error?**

La guardé en `local.properties` porque es un archivo de configuración local que está excluido del repositorio mediante `.gitignore`. De esta manera, evito subir la clave directamente a GitHub. Gradle se encarga de leerla y pasarla al Manifest. Si por error la subiera a GitHub, tendría que revocarla y generar una nueva, configurando sus restricciones de paquete, SHA-1 y API. También tendría que revisar el historial de Git para asegurarme de que la clave expuesta ya no siga disponible.

Igualmente, aunque la clave esté en `local.properties`, eso no significa que sea completamente privada, porque puede quedar dentro de la aplicación compilada. Por eso también es importante configurar sus restricciones.

### **4. ¿Dónde calculas la distancia y por qué no en el Composable?**

El cálculo lo hago en `util/Distancia.kt`, utilizando `Location.distanceBetween`. Cuando el ViewModel recibe la ubicación del usuario, llama a esa función para calcular las distancias, ordenar los lugares y actualizar la lista. No lo hago directamente en el Composable porque este se encarga principalmente de mostrar la información. Así mantengo separado el cálculo de la interfaz y evito que se repita innecesariamente cuando la pantalla se recompone.

### **5. ¿Qué pasa con tu app si el usuario niega el permiso dos veces? ¿Cómo lo manejas?**

Si el usuario niega el permiso varias veces, Android puede dejar de mostrar la ventana para solicitarlo nuevamente. En ese caso, la aplicación no puede obligar al sistema a mostrarla otra vez. Lo que hago en CampusGPS es mostrar un mensaje indicando que el permiso fue denegado y colocar un botón de «Abrir ajustes», para que el usuario pueda entrar a la configuración de la aplicación y habilitarlo manualmente. Cuando regresa y pulsa «Obtener mi ubicación», la aplicación vuelve a comprobar si tiene el permiso antes de intentar obtener su ubicación.
