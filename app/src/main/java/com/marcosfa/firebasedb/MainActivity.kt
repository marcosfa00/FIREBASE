package com.marcosfa.firebasedb

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.marcosfa.firebasedb.model.DataUser
import com.marcosfa.firebasedb.model.TAG
import com.marcosfa.firebasedb.model.TAG2
import com.marcosfa.firebasedb.model.repository
import com.marcosfa.firebasedb.ui.LogInView
import com.marcosfa.firebasedb.ui.ShowRegister
import com.marcosfa.firebasedb.ui.WelcomeText
import com.marcosfa.firebasedb.ui.WelcomeTextADMIN
import com.marcosfa.firebasedb.ui.theme.FirebasedbTheme
import com.marcosfa.firebasedb.viewModel.myViewModel


class MainActivity : ComponentActivity() {
    //Authentication lateinit indica que se va a crear más tarde
    lateinit var firebaseAuth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        firebaseAuth = Firebase.auth

        // Inicializa Firebase
        FirebaseApp.initializeApp(this)
        // Si estás utilizando Firebase Analytics, también deberías inicializarlo
        val analytics = FirebaseAnalytics.getInstance(this)
        setContent {
            FirebasedbTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val model = repository
                    val viewModel = myViewModel(model)
                    Greeting(viewModel, firebaseAuth)
                    Log.d(TAG2,"Usuario Autentificaco al iniciar la ap ${firebaseAuth.currentUser?.uid}")

                }
            }
        }

    }


    override fun onStart() {
        super.onStart()

    }
}

@Composable
fun Greeting(vModel:myViewModel, auth: FirebaseAuth) {

    if (DataUser.state.value == DataUser.State.REGISTRO){
        ShowRegister(vModel, auth)
    }else if (DataUser.state.value == DataUser.State.LOGIN){
        LogInView(viewModel = vModel, autentificacion = auth)
    }else if(DataUser.state.value == DataUser.State.HOME){
        WelcomeText(vModel)
    }else if (DataUser.state.value == DataUser.State.ADMIN){
        WelcomeTextADMIN(vModel)

    }


}