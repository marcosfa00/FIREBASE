package com.marcosfa.firebasedb.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcosfa.firebasedb.model.DataUser
import com.marcosfa.firebasedb.model.TAG
import com.marcosfa.firebasedb.model.User
import com.marcosfa.firebasedb.model.repository
import kotlinx.coroutines.launch

//To work directly9 with the repository Methods the injection of dependences is obligatory in MVVM design Patron
class myViewModel(private val model: repository ): ViewModel() {


    /**
     * Al inicializar el viewModel en el MainActivity.kt
     * se cargará las funciones que llamemos desde el init
     */
    init{
        listenForUserChanges()
    }

    private fun listenForUserChanges() {
        viewModelScope.launch {
            // Escucha cambios en la colección de usuarios
            model.listenForUserChanges().collect { userList ->
                // Actualiza la lista de usuarios
                DataUser.users.value = userList
            }
        }
    }





    fun addUser(user: User) {
        model.addUser(user)
            .addOnSuccessListener {
                // Actualiza la lista de usuarios después de agregar uno nuevo
               listenForUserChanges()
            }
            .addOnFailureListener { e ->
                // Maneja errores de agregar usuario
            }
    }





}