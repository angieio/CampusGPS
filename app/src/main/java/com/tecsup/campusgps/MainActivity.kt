package com.tecsup.campusgps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.tecsup.campusgps.data.LugaresRepositoryLocal
import com.tecsup.campusgps.data.UbicacionRepositoryGoogle
import com.tecsup.campusgps.ui.PantallaMapa
import com.tecsup.campusgps.ui.theme.CampusGPSTheme
import com.tecsup.campusgps.viewmodel.CampusViewModelFactory
import com.tecsup.campusgps.viewmodel.MapaViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val factory = CampusViewModelFactory(
            ubicacionRepo = UbicacionRepositoryGoogle(
                applicationContext
            ),
            lugaresRepo = LugaresRepositoryLocal()
        )

        val vm = ViewModelProvider(
            this,
            factory
        )[MapaViewModel::class.java]

        setContent {
            CampusGPSTheme {
                PantallaMapa(vm = vm)
            }
        }
    }
}