package com.tecsup.campusgps.data

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.tecsup.campusgps.model.Posicion
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

class UbicacionNoDisponibleException :
    IllegalStateException("Activa el GPS e intenta de nuevo")

class UbicacionRepositoryGoogle(
    context: Context
) : UbicacionRepository {

    private val contexto = context.applicationContext

    private val cliente: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(contexto)

    override fun playServicesDisponible(): Boolean {
        return GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(contexto) ==
                ConnectionResult.SUCCESS
    }

    @SuppressLint("MissingPermission")
    override suspend fun obtenerPosicion(): Result<Posicion> {
        val cancelacion = CancellationTokenSource()

        return try {
            val locationManager = contexto.getSystemService(
                Context.LOCATION_SERVICE
            ) as LocationManager

            if (!LocationManagerCompat.isLocationEnabled(locationManager)) {
                throw UbicacionNoDisponibleException()
            }

            val loc = cliente.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancelacion.token
            ).await() ?: throw UbicacionNoDisponibleException()

            Result.success(
                Posicion(
                    latitud = loc.latitude,
                    longitud = loc.longitude
                )
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        } finally {
            cancelacion.cancel()
        }
    }
}