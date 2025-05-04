package com.example.gardenappdefinitive

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun GardenScreen(navController: NavController) {
    val plantaNombre = "ROMERITO"
    val fotoPlanta = painterResource(id = R.drawable.plantita_hacker)
    val fondo = painterResource(id = R.drawable.fondo_pantalla_screen2)
    var regando by remember { mutableStateOf(false) } // Estado del botón
    var humedad by remember { mutableStateOf("Cargando...") } // Estado de la humedad
    var temperatura by remember { mutableStateOf("Cargando...") } // Estado de la temperatura

    // Llamada periódica a la API para obtener la humedad y la temperatura
    LaunchedEffect(true) {
        while (true) {
            delay(2000)
            obtenerHumedadYTemperatura { nuevaHumedad, nuevaTemperatura ->
                humedad = nuevaHumedad
                temperatura = nuevaTemperatura
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        // Fondo
        Image(
            painter = fondo,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Puedes usar Fit, FillBounds, etc. según prefieras
        )
        Column (
            modifier = Modifier
                .fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(150.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Text(
                    text = plantaNombre,
                    color = Color(0xFF2E7D32),
                    textAlign = TextAlign.Center
                )
            }
            Image(
                painter = fotoPlanta,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly

            ){
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .background(Color(0xFFDFF0D8), shape = RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = "Humedad",
                            color = Color(0xFF2E7D32),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre las dos cajas

                        Text(
                            text = humedad+"%",
                            color = Color(0xFF2E7D32),
                            textAlign = TextAlign.Center
                        )
                    }

                }
                Spacer(modifier = Modifier.width(16.dp)) // Espacio entre las dos cajas

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                        .background(Color(0xFFDFF0D8), shape = RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = "Temperatura",
                            color = Color(0xFF2E7D32),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre las dos cajas

                        Text(
                            text = temperatura+"°C",
                            color = Color(0xFF2E7D32),
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center

            ){
                Button(
                    modifier = Modifier
                        .height(150.dp)
                        .width(150.dp),
                    shape = RoundedCornerShape(12.dp), // <- aquí se aplica el borde redondeado

                    onClick = {
                        regando = !regando // Alternar estado
                        // Lanzamos la solicitud en un hilo diferente (corutina)
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val stream = java.net.URL("http://192.168.174.5/servo").openStream()
                                val s = String(stream.readAllBytes())
                                Log.d("GardenScreen", "Respuesta del servidor: $s")
                                stream.close()

                                // Volvemos al hilo principal para actualizar la UI si es necesario
                                withContext(Dispatchers.Main) {
                                    // Aquí puedes hacer algo con la respuesta si es necesario
                                }
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }
                        }
                    }

                ) {
                    Text(if (regando) "Parar de regar" else "Riega a Romerito")
                }
            }

        }
    }


}

// Esta función ahora obtiene tanto la humedad como la temperatura
fun obtenerHumedadYTemperatura(onResult: (String, String) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val url = URL("http://192.168.174.5/dht")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonResponse = JSONObject(response)
                val humedad = jsonResponse.getString("hum")
                val temperatura = jsonResponse.getString("temp")

                // Volver al hilo principal para actualizar la UI
                withContext(Dispatchers.Main) {
                    onResult(humedad, temperatura)
                }
            } else {
                Log.e("obtenerHumedadYTemperatura", "Error al obtener datos de la API")
            }
            connection.disconnect()
        } catch (e: Exception) {
            Log.e("obtenerHumedadYTemperatura", "Error: ${e.message}")
        }
    }
}
