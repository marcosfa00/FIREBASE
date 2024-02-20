package com.marcosfa.firebasedb.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marcosfa.firebasedb.model.DataUser

@Composable
fun WelcomeText() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            Text(
                text = "Bienvenido",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )

            ButtonVolver()
        }

    }
}


@Composable
fun ButtonVolver(){
    Button(onClick = {

        DataUser.state.value = DataUser.State.LOGIN


    }) {
        Text(text = "CERRAR SESIÓN")
    }
}







