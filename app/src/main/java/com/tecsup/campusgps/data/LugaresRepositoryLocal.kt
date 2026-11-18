package com.tecsup.campusgps.data

import com.tecsup.campusgps.model.Lugar

class LugaresRepositoryLocal : LugaresRepository {

    override fun obtenerLugares(): List<Lugar> = listOf(
        Lugar(
            id = 1,
            nombre = "Biblioteca Central",
            categoria = "Estudio",
            latitud = -12.0445,
            longitud = -76.9548
        ),
        Lugar(
            id = 2,
            nombre = "Cafetería Campus",
            categoria = "Comida",
            latitud = -12.0450,
            longitud = -76.9542
        ),
        Lugar(
            id = 3,
            nombre = "Laboratorio de Cómputo",
            categoria = "Estudio",
            latitud = -12.0442,
            longitud = -76.9543
        ),
        Lugar(
            id = 4,
            nombre = "Auditorio Principal",
            categoria = "Eventos",
            latitud = -12.0453,
            longitud = -76.9546
        ),
        Lugar(
            id = 5,
            nombre = "Estacionamiento Norte",
            categoria = "Servicios",
            latitud = -12.0438,
            longitud = -76.9549
        )
    )
}