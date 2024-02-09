package com.marcosfa.firebasedb.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.marcosfa.firebasedb.model.DataUser
import com.marcosfa.firebasedb.model.User
import com.marcosfa.firebasedb.viewModel.myViewModel

@Composable
fun ShowRegister(vModel: myViewModel){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Register()
        ButtonRegister(vModel)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(){

    OutlinedTextField(
        value = DataUser.id.value,
        onValueChange = { DataUser.id.value = it },
        label = { Text("id") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )

    OutlinedTextField(
        value = DataUser.name.value,
        onValueChange = { DataUser.name.value = it },
        label = { Text("name") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
    OutlinedTextField(
        value = DataUser.age.value,
        onValueChange = {
           DataUser.age.value = it
                        },
        label = { Text("age") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    OutlinedTextField(
        value = DataUser.gmail.value,
        onValueChange = {DataUser.gmail.value = it.lowercase() },
        label = { Text("gmail") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}


@Composable
fun ButtonRegister(vModel: myViewModel){
    Button(onClick = {

           vModel.addUser(User(DataUser.id.value,DataUser.name.value,DataUser.age.value.toString(),DataUser.gmail.value))
    }) {
        Text(text = "SIGN UP")
    }
}