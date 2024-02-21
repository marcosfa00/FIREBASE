package com.marcosfa.firebasedb.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import com.marcosfa.firebasedb.model.DataUser
import com.marcosfa.firebasedb.viewModel.myViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LogInView(viewModel: myViewModel, autentificacion: FirebaseAuth) {
    val systemUiController = rememberSystemUiController()
    val view = LocalView.current
    val viewInfo = LocalWindowInfo.current
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    systemUiController.setSystemBarsColor(MaterialTheme.colorScheme.background.toArgb())
    systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.background.toArgb())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = DataUser.gmail.value,
            onValueChange = { DataUser.gmail.value = it.lowercase() },
            label = { Text("gmail") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            leadingIcon = {
                Icon(Icons.Default.MailOutline, contentDescription = "Email Icon")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))



            OutlinedTextField(
                value = DataUser.password.value,
                onValueChange = { DataUser.password.value = it },
                label = { Text("password") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "Password Icon")
                }

            )


        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = {
                softwareKeyboardController?.hide()
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

private fun <SystemUiController> SystemUiController.setNavigationBarColor(color: Int) {

}

private fun <SystemUiController> SystemUiController.setSystemBarsColor(color: Int) {

}




