# Historial de Prompts - CampusGPS

## Prompt 1: Lugares dentro de Tecsup (2026-11-18)
Actúa como un desarrollador Android con experiencia y ayúdame a actualizar los lugares de CampusGPS. Mi app está hecha con Kotlin, Jetpack Compose y Material 3, y usa MVVM + Repository. Estoy trabajando en la rama mejora-ia.

Actualmente los cinco marcadores están en el centro de Lima. Quiero ubicarlos dentro del campus de Tecsup en Avenida Cascanueces 2221, Santa Anita, Lima. Mantén los nombres Biblioteca Central, Cafetería Campus, Laboratorio de Cómputo, Auditorio Principal y Estacionamiento Norte, con sus mismos identificadores y categorías.

Primero revisa el proyecto y busca una fuente confiable para ubicar el campus. Muéstrame las coordenadas propuestas antes de aplicarlas. Si no puedes verificar la posición real de cada edificio, aclara que son puntos ficticios de demostración dentro del campus. Si no puedes confirmar que están dentro, indícamelo y no inventes esa verificación.

Actualiza las coordenadas en el repositorio local y revisa la cámara inicial y el punto de prueba para que correspondan a Tecsup. No pongas los datos directamente en el composable. Conserva los permisos, errores, distancias, selección y lugar más cercano.

Haz únicamente esta actualización, sin agregar otras mejoras. No leas ni muestres mi clave de API, no modifiques local.properties y no hagas commits automáticamente. Al terminar, explica qué cambió, comprueba que compile y dime cómo probarlo.
