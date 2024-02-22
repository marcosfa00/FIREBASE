package com.marcosfa.firebasedb.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.marcosfa.firebasedb.model.DataUser
import com.marcosfa.firebasedb.viewModel.myViewModel
import java.net.Authenticator

@Composable
fun WelcomeText(viewModel: myViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column {
            Text(
                text = "Bienvenido",
                modifier = Modifier

                    .padding(16.dp),
                style = TextStyle(
                    color = Color.Magenta,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
            ButtonVolver()
            DatosUsuario(viewModel)
            ButtonDeleteAccount(viewModel )
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


@Composable
fun DatosUsuario(viewModel: myViewModel){

   // val currentUser = viewModel.getDataCurrentUser(DataUser.gmail.value)
    viewModel.getuserById(DataUser.currentID.value)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                //TODO on click Card
            }

    ) {
        Column {
            // id_Database
            Text(
                text = ("ID: " + DataUser.currentID.value) ?: "Uid no Disponible",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // name
            Text(
                text =("Nombre: "+ DataUser.userConnected.value?.name )?: "Nombre no disponible",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // age
            Text(
                text =("Edad: "+  DataUser.userConnected.value?.age) ?: "Nombre no disponible",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // gmail
            Text(
                text = ("Gmail: "+ DataUser.userConnected.value?.gmail) ?: "Gmail No disponible",
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}



@Composable
fun ButtonDeleteAccount(viewModel: myViewModel){
    Button(onClick = {
        viewModel.deleteAccount()
        DataUser.state.value = DataUser.State.LOGIN


    }) {
        Text(text = "ELIMINAR MI CUENTA")
    }

}















