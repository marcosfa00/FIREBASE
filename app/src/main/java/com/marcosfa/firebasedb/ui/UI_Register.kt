package com.marcosfa.firebasedb.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.marcosfa.firebasedb.model.DataUser
import com.marcosfa.firebasedb.model.TAG
import com.marcosfa.firebasedb.model.User
import com.marcosfa.firebasedb.viewModel.myViewModel

@Composable
fun ShowRegister(vModel: myViewModel, autentification: FirebaseAuth){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Register()
        Row {
            ButtonRegister(vModel, autentification)
            ButtonLogearse()
        }


        ShowAllUsers(DataUser.users.value)

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(){



    OutlinedTextField(
        value = DataUser.name.value,
        onValueChange = { DataUser.name.value = it },
        label = { Text("name") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        leadingIcon = {
            Icon(Icons.Default.AccountCircle, contentDescription = "Name icon")
        }
    )
    OutlinedTextField(
        value = DataUser.age.value,
        onValueChange = {
           DataUser.age.value = it
                        },
        label = { Text("age") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        leadingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = "Age icon")
        }
    )
    OutlinedTextField(
        value = DataUser.gmail.value,
        onValueChange = {DataUser.gmail.value = it.lowercase() },
        label = { Text("gmail") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        leadingIcon = {
            Icon(Icons.Default.MailOutline, contentDescription = "Mail Icon")
        }
    )
    var passwordVisible by remember { mutableStateOf(true) }
    OutlinedTextField(
        value = DataUser.password.value,
        onValueChange = { DataUser.password.value = it },
        label = { Text("password") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        visualTransformation = if (passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        leadingIcon = {
            val icon = if (passwordVisible) Icons.Default.Lock else Icons.Default.Search
            Icon(icon, contentDescription = "Password Icon", Modifier.clickable {
                passwordVisible = !passwordVisible
            })
        }

    )
}


@Composable
fun ButtonRegister(vModel: myViewModel, autentification: FirebaseAuth){
    Button(onClick = {

            val user = User(DataUser.name.value,DataUser.age.value.toString(),DataUser.gmail.value, false)
           vModel.addUser(user,autentification)


    }) {
        Text(text = "SIGN UP")
    }
}


@Composable
fun ButtonLogearse(){
    Button(onClick = {
       // FirebaseAuth.getInstance().signOut()
        DataUser.state.value = DataUser.State.LOGIN


    }) {
        Text(text = "LOGEARSE")
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