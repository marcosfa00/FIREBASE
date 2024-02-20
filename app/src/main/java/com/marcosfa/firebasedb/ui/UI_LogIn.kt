package com.marcosfa.firebasedb.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.marcosfa.firebasedb.model.DataUser
import com.marcosfa.firebasedb.viewModel.myViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInView(viewModel: myViewModel, autentificacion: FirebaseAuth) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = DataUser.gmail.value,
            onValueChange = { DataUser.gmail.value = it },
            label = { Text("gmail") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = DataUser.password.value,
            onValueChange = { DataUser.password.value = it },
            label = { Text("password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = {
                viewModel.loginUser(DataUser.gmail.value, DataUser.password.value, autentificacion)
            }) {
                Text(text = "Log In")
            }

            Button(onClick = {
                DataUser.state.value = DataUser.State.REGISTRO
            }) {
                Text(text = "Volver al Inicio")
            }
        }

    }
}




