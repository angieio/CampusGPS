package com.tecsup.campusgps.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.campusgps.data.LugaresRepository
import com.tecsup.campusgps.data.UbicacionNoDisponibleException
import com.tecsup.campusgps.data.UbicacionRepository
import com.tecsup.campusgps.model.Lugar
import com.tecsup.campusgps.model.LugarConDistancia
import com.tecsup.campusgps.model.Posicion
import com.tecsup.campusgps.model.TipoError
import com.tecsup.campusgps.model.UbicacionUi
import com.tecsup.campusgps.util.distanciaMetros
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MapaViewModel(
    private val ubicacionRepo: UbicacionRepository,
    private val lugaresRepo: LugaresRepository
) : ViewModel() {

    private val _estado =
        MutableStateFlow<UbicacionUi>(UbicacionUi.Inicial)

    val estado: StateFlow<UbicacionUi> = _estado.asStateFlow()

    private val _categoriaSeleccionada = MutableStateFlow("Todas")
    val categoriaSeleccionada: StateFlow<String> = _categoriaSeleccionada.asStateFlow()

    private val _lugaresCompletos = MutableStateFlow(
        lugaresRepo.obtenerLugares().map {
            LugarConDistancia(it, null)
        }
    )

    private val _lugares = MutableStateFlow(
        lugaresRepo.obtenerLugares().map {
            LugarConDistancia(it, null)
        }
    )

    val lugares: StateFlow<List<LugarConDistancia>> =
        _lugares.asStateFlow()

    private val _seleccionado = MutableStateFlow<Int?>(null)

    val seleccionado: StateFlow<Int?> =
        _seleccionado.asStateFlow()

    private val _solicitudSeleccion = MutableStateFlow(0L)
    val solicitudSeleccion: StateFlow<Long> =
        _solicitudSeleccion.asStateFlow()

    fun seleccionarCategoria(categoria: String) {
        _categoriaSeleccionada.value = categoria
        val todos = _lugaresCompletos.value
        val filtrados = when (categoria) {
            "Todas" -> todos
            else -> todos.filter { it.lugar.categoria.equals(categoria, ignoreCase = true) }
        }
        _lugares.value = filtrados

        val seleccionadoActual = _seleccionado.value
        if (seleccionadoActual != null && filtrados.none { it.lugar.id == seleccionadoActual }) {
            _seleccionado.value = null
        }
    }

    fun buscar() {
        if (!ubicacionRepo.playServicesDisponible()) {
            _estado.value = UbicacionUi.Error(
                mensaje = "Este dispositivo no tiene Google Play Services",
                tipo = TipoError.SIN_PLAY_SERVICES
            )
            return
        }

        _estado.value = UbicacionUi.Buscando

        viewModelScope.launch {
            ubicacionRepo.obtenerPosicion()
                .onSuccess { posicion ->
                    _estado.value = UbicacionUi.Lista(posicion)
                    actualizarDistancias(posicion)
                }
                .onFailure { error ->
                    _estado.value = when (error) {
                        is SecurityException -> UbicacionUi.Error(
                            mensaje = "Falta el permiso de ubicación",
                            tipo = TipoError.SIN_PERMISO
                        )

                        is UbicacionNoDisponibleException ->
                            UbicacionUi.Error(
                                mensaje = "Activa el GPS e intenta de nuevo",
                                tipo = TipoError.GPS_APAGADO
                            )

                        else -> UbicacionUi.Error(
                            mensaje = error.message
                                ?: "No se pudo obtener la ubicación",
                            tipo = TipoError.OTRO
                        )
                    }
                }
        }
    }

    private fun actualizarDistancias(pos: Posicion) {
        val ordenados = lugaresRepo.obtenerLugares()
            .map { lugar ->
                LugarConDistancia(
                    lugar = lugar,
                    metros = distanciaMetros(pos, lugar)
                )
            }
            .sortedBy { it.metros }

        _lugaresCompletos.value = ordenados

        val cat = _categoriaSeleccionada.value
        val filtrados = when (cat) {
            "Todas" -> ordenados
            else -> ordenados.filter { it.lugar.categoria.equals(cat, ignoreCase = true) }
        }
        _lugares.value = filtrados

        val seleccionadoActual = _seleccionado.value
        if (seleccionadoActual != null && filtrados.none { it.lugar.id == seleccionadoActual }) {
            _seleccionado.value = null
        }

        Log.d(
            "CampusGPS",
            filtrados.joinToString("\n") {
                "${it.lugar.nombre}: ${it.metros} m"
            }
        )
    }

    fun permisoDenegado() {
        _estado.value = UbicacionUi.Error(
            mensaje = "Sin permiso de ubicación no puedo mostrar dónde estás",
            tipo = TipoError.SIN_PERMISO
        )
    }

    fun seleccionar(id: Int?) {
        _seleccionado.value = id
        _solicitudSeleccion.value += 1
    }

    val masCercano: StateFlow<Lugar?> = lugares
        .map { lista ->
            lista.firstOrNull { it.metros != null }?.lugar
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null
        )

}