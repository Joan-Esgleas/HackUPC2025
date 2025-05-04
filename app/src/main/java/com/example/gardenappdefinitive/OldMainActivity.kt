package com.example.gardenappdefinitive

import GardenApp
import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gardenappdefinitive.ui.theme.GardenAppDefinitiveTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import com.example.gardenappdefinitive.model.Planta



//data class Planta(val nombre: String, val humedad: Int)

class OldMainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GardenAppDefinitiveTheme {
                // Llamamos a la función Composable que contiene el estado de la UI
                GardenApp()
            }
        }
    }
}


//@Composable
//fun MapaDePlantas(plantas: MutableList<Planta>) {
//    // Un mapa que guarda cuántas veces se ha clicado cada planta
//    val clickCounts = remember { mutableStateMapOf<String, Int>() }
//
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(3), // Tres columnas
//        contentPadding = PaddingValues(8.dp)
//    ) {
//        items(plantas) { planta ->
//            PlantaCard(
//                planta = planta,
//                count = clickCounts[planta.nombre] ?: 0,
//                onClick = {
//                    val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//                    StrictMode.setThreadPolicy(policy)
//                    val men = "Test"
//                    val ip = InetAddress.getByName("192.168.205.174")
//                    val port = 80
//                    try {
//                        val socket = DatagramSocket()
//                        socket.broadcast = true
//                        val SendData = men.toByteArray()
//                        val sendPacket = DatagramPacket(SendData, SendData.size, ip, port)
//                        socket.send(sendPacket)
//                    } catch (e: IOException) {
//                        val current = clickCounts[planta.nombre] ?: 0
//                        clickCounts[planta.nombre] = current + 1
//                    }
//                },
//                onDelete = {
//                    plantas.remove(planta)
//                }
//            )
//        }
//    }
//}


