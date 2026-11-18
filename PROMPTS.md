# Historial de Prompts - CampusGPS

## Prompt 1: Lugares dentro de Tecsup (2026-11-18)
Actúa como un desarrollador Android con experiencia y ayúdame a actualizar los lugares de CampusGPS. Mi app está hecha con Kotlin, Jetpack Compose y Material 3, y usa MVVM + Repository. Estoy trabajando en la rama mejora-ia.

Actualmente los cinco marcadores están en el centro de Lima. Quiero ubicarlos dentro del campus de Tecsup en Avenida Cascanueces 2221, Santa Anita, Lima. Mantén los nombres Biblioteca Central, Cafetería Campus, Laboratorio de Cómputo, Auditorio Principal y Estacionamiento Norte, con sus mismos identificadores y categorías.

Primero revisa el proyecto y busca una fuente confiable para ubicar el campus. Muéstrame las coordenadas propuestas antes de aplicarlas. Si no puedes verificar la posición real de cada edificio, aclara que son puntos ficticios de demostración dentro del campus. Si no puedes confirmar que están dentro, indícamelo y no inventes esa verificación.

Actualiza las coordenadas en el repositorio local y revisa la cámara inicial y el punto de prueba para que correspondan a Tecsup. No pongas los datos directamente en el composable. Conserva los permisos, errores, distancias, selección y lugar más cercano.

Haz únicamente esta actualización, sin agregar otras mejoras. No leas ni muestres mi clave de API, no modifiques local.properties y no hagas commits automáticamente. Al terminar, explica qué cambió, comprueba que compile y dime cómo probarlo.

## Prompt 2: Filtrar por categoría (2026-11-18)
Actúa como un desarrollador Android con experiencia y ayúdame a agregar filtros por categoría a CampusGPS, conservando el estilo visual que ya tiene. El proyecto usa Kotlin, Jetpack Compose, Material 3 y MVVM + Repository, y estoy trabajando en mejora-ia.

Quiero chips para Todas, Estudio, Comida, Eventos y Servicios. Al seleccionar una categoría, deben mostrarse únicamente sus lugares tanto en el mapa como en la lista. Al elegir Todas, deben volver a aparecer los cinco lugares.

Revisa primero el código y explícame qué archivos cambiarás. Guarda la categoría seleccionada y prepara la lista filtrada en el ViewModel; la pantalla solo debe mostrar el resultado y comunicar lo que selecciono. Conserva la lista completa y el orden por distancia. El lugar más cercano debe corresponder al filtro actual, y si el lugar seleccionado queda oculto, limpia esa selección.

No cambies las coordenadas ni agregues otras funciones. Conserva permisos, errores y navegación del mapa. No agregues Context al ViewModel ni consultas a repositorios desde los composables. No leas ni muestres mi clave de API y no modifiques local.properties.

Comprueba que compile y dime cómo probar cada categoría, volver a Todas y verificar que mapa, lista y lugar más cercano coincidan. No hagas el commit automáticamente.

## Prompt 3: Radio de cercanía (2026-11-18)
Actúa como un desarrollador Android con experiencia y ayúdame a incorporar un radio de cercanía en CampusGPS. Trabaja sobre lo que ya está implementado en mejora-ia, respetando Kotlin, Jetpack Compose, Material 3 y MVVM + Repository.

Quiero elegir entre 100, 200 y 500 metros. Al obtener mi ubicación, debe aparecer un círculo alrededor de mi posición con el radio seleccionado. En la lista debe distinguirse claramente cuáles lugares están dentro del radio, sin ocultar los que están fuera y sin reemplazar el filtro por categoría.

Primero revisa el proyecto y explícame qué archivos necesitas cambiar. Guarda el radio en el ViewModel y determina allí qué lugares están dentro, aprovechando las distancias que ya calcula la app. Los composables solo deben dibujar el círculo, mostrar los indicadores y comunicar el radio elegido. Antes de obtener ubicación no debe aparecer el círculo.

Conserva los filtros, las coordenadas, el orden de la lista, la selección, los colores de los marcadores y el botón del más cercano. No agregues Context al ViewModel, no calcules distancias en la pantalla y no implementes otras mejoras.

No leas ni muestres mi clave de API, no modifiques local.properties ni hagas commits automáticamente. Comprueba que compile y explícame cómo probar los tres radios, combinarlos con los filtros y cambiar la ubicación.
