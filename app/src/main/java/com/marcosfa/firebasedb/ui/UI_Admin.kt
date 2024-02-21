package com.marcosfa.firebasedb.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marcosfa.firebasedb.model.DataUser
import com.marcosfa.firebasedb.viewModel.myViewModel


@Composable
fun WelcomeTextADMIN(viewModel: myViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column {
            Text(
                text = "Bienvenido ADMIN",
                modifier = Modifier

                    .padding(16.dp),
                style = TextStyle(
                    color = Color.Magenta,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
            ButtonVolverAdmin()
            DatosUsuarioAdmin(viewModel)
            TextGmail()
            BtnDelete(viewModel)
            BtnUpdate(viewModel)

        }

    }
}


@Composable
fun ButtonVolverAdmin(){
    Button(onClick = {

        DataUser.state.value = DataUser.State.LOGIN


    }) {
        Text(text = "CERRAR SESIÓN")
    }
}


@Composable
fun DatosUsuarioAdmin(viewModel: myViewModel){

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextGmail(){
    OutlinedTextField(
        value = DataUser.gmailUsuarioAEliminar.value,
        onValueChange = { DataUser.gmailUsuarioAEliminar.value = it },
        label = { Text("Gmail Usuario a Eliminar") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )

    OutlinedTextField(
        value = DataUser.age.value,
        onValueChange = { DataUser.age.value = it },
        label = { Text("Actualizar EDAD") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun BtnDelete(viewModel: myViewModel){
    Button(onClick = {
        // FirebaseAuth.getInstance().signOut() IMPORTANTE VER ESTO

        viewModel.deleteUser(DataUser.gmailUsuarioAEliminar.value)

    }) {
        Text(text = "ELIMINAR USUARIO")
    }
}

@Composable
fun BtnUpdate(viewModel: myViewModel){
    Button(onClick = {
       viewModel.updateAge(DataUser.age.value)

    }) {
        Text(text = "ACTUALIZAR USUARIO")
    }
}

