package com.tu_paquete.ui

import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gardenappdefinitive.R // Asegúrate de importar el paquete de recursos

@Composable
fun WelcomeScreen(navController: NavController) {
    // Cargar la imagen de fondo desde los recursos
    val fondo = painterResource(id = R.drawable.fondo_screen1) // Cambia "fondo_gatete" por el nombre de tu archivo .png

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Imagen de fondo
        Image(
            painter = fondo,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        // El contenido de la pantalla (botón, texto, etc.)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    navController.navigate("GardenScreen")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32), //poner aquí el código del color que queremos poner
                    contentColor = Color.White
                )

            ) {
                Text("Ir al jardín de plantas")
            }
        }
    }
}