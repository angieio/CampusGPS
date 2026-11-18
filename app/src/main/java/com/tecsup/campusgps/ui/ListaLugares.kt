package com.tecsup.campusgps.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.campusgps.model.LugarConDistancia
import com.tecsup.campusgps.util.formatearDistancia

@Composable
fun ListaLugares(
    lugares: List<LugarConDistancia>,
    seleccionado: Int?,
    radio: Float,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(
            items = lugares,
            key = { it.lugar.id }
        ) { elemento ->
            val lugar = elemento.lugar
            val estaSeleccionado = lugar.id == seleccionado

            Card(
                onClick = { onClick(lugar.id) },
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (estaSeleccionado) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                ),
                border = if (estaSeleccionado) {
                    BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.primary
                    )
                } else {
                    null
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                    )

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = lugar.nombre,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = lugar.categoria,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            val enRadio = elemento.metros != null && elemento.metros <= radio
                            if (enRadio) {
                                Text(
                                    text = "• Dentro del radio",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    Text(
                        text = elemento.metros?.let {
                            formatearDistancia(it)
                        } ?: "Sin calcular",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}