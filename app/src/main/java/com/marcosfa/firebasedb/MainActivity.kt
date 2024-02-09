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
import com.marcosfa.firebasedb.model.TAG
import com.marcosfa.firebasedb.model.repository
import com.marcosfa.firebasedb.ui.ShowRegister
import com.marcosfa.firebasedb.ui.theme.FirebasedbTheme
import com.marcosfa.firebasedb.viewModel.myViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
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
                    Greeting(viewModel)
                }
            }
        }

    }
}

@Composable
fun Greeting(vModel:myViewModel) {
 ShowRegister(vModel)
}

