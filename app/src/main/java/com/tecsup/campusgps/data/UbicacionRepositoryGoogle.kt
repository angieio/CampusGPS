package com.tecsup.campusgps.data

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.tecsup.campusgps.model.Posicion
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class UbicacionRepositoryGoogle(
    context: Context
) : UbicacionRepository {

    private val cliente: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(
            context.applicationContext
        )

    @SuppressLint("MissingPermission")
    override suspend fun obtenerPosicion(): Result<Posicion> {
        val cancelacion = CancellationTokenSource()

        return try {
            val loc = cliente.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancelacion.token
            ).await()

            if (loc != null) {
                Result.success(
                    Posicion(
                        latitud = loc.latitude,
                        longitud = loc.longitude
                    )
                )
            } else {
                Result.failure(
                    IllegalStateException("Activa el GPS e intenta de nuevo")
                )
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        } finally {
            cancelacion.cancel()
        }
    }
}