package com.tecsup.campusgps.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.tecsup.campusgps.model.LugarConDistancia
import com.tecsup.campusgps.model.UbicacionUi

@Composable
fun MapaUbicacion(
    estado: UbicacionUi,
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    solicitudSeleccion: Long,
    modifier: Modifier = Modifier
) {
    val camara = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(-12.0448, -76.9545),
            15f
        )
    }

    LaunchedEffect(estado) {
        if (estado is UbicacionUi.Lista) {
            val posicion = LatLng(
                estado.posicion.latitud,
                estado.posicion.longitud
            )

            camara.animate(
                CameraUpdateFactory.newLatLngZoom(posicion, 16f)
            )
        }
    }

    LaunchedEffect(seleccionado, solicitudSeleccion) {
        val lugar = lugares
            .firstOrNull { it.lugar.id == seleccionado }
            ?.lugar

        if (lugar != null) {
            camara.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(lugar.latitud, lugar.longitud),
                    17f
                )
            )
        }
    }

    val idMasCercano = lugares
        .firstOrNull { it.metros != null }
        ?.lugar
        ?.id

    GoogleMap(
        modifier = modifier,
        cameraPositionState = camara
    ) {
        if (estado is UbicacionUi.Lista) {
            val posicion = LatLng(
                estado.posicion.latitud,
                estado.posicion.longitud
            )

            Marker(
                state = rememberUpdatedMarkerState(
                    position = posicion
                ),
                title = "Estás aquí"
            )
        }

        lugares.forEach { lugarConDistancia ->
            val lugar = lugarConDistancia.lugar

            key(lugar.id) {
                val marcador = rememberUpdatedMarkerState(
                    position = LatLng(
                        lugar.latitud,
                        lugar.longitud
                    )
                )

                Marker(
                    state = marcador,
                    title = lugar.nombre,
                    snippet = lugar.categoria,
                    icon = BitmapDescriptorFactory.defaultMarker(
                        if (lugar.id == idMasCercano) {
                            BitmapDescriptorFactory.HUE_GREEN
                        } else {
                            BitmapDescriptorFactory.HUE_AZURE
                        }
                    )
                )

                LaunchedEffect(seleccionado, solicitudSeleccion) {
                    if (seleccionado == lugar.id) {
                        marcador.showInfoWindow()
                    } else {
                        marcador.hideInfoWindow()
                    }
                }
            }
        }
    }
}