package com.marcosfa.firebasedb.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marcosfa.firebasedb.model.DataUser
import com.marcosfa.firebasedb.model.TAG
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
        ShowAllUsers(DataUser.users.value)

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


@Composable
fun ShowAllUsers(lista :List<User>){
LazyColumn{
    items(lista) {users ->
        UserCard(user = users)
    }

}



}

@Composable
fun UserCard(user:User){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                //TODO on click Card
            }

    ){
        Column {
            // id
            Text(
                text = user.id,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // name
            Text(
                text = user.name,
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // age
            Text(
                text = user.age,
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // gmail
            Text(
                text = user.gmail,
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}