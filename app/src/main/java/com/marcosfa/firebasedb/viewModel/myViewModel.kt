package com.marcosfa.firebasedb.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marcosfa.firebasedb.model.TAG
import com.marcosfa.firebasedb.model.User
import com.marcosfa.firebasedb.model.repository

//To work directly9 with the repository Methods the injection of dependences is obligatory in MVVM design Patron
class myViewModel(private val model: repository ): ViewModel() {

   private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    /**
     * Al inicializar el viewModel en el MainActivity.kt
     * se cargará las funciones que llamemos desde el init
     */
    init{
       loadUsers()
    }

    private fun loadUsers() {
        model.getUsers()
            .addOnSuccessListener { querySnapshot ->
                val userList = mutableListOf<User>()
                for (document in querySnapshot.documents) {
                    val user = document.toObject(User::class.java)
                    user?.let {
                        userList.add(it)
                    }
                }
                _users.value = userList
                Log.d(TAG, "Users loaded successfully: ${userList.size} users")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error loading users", e)
            }
    }



    fun addUser(user: User) {
        model.addUser(user)
            .addOnSuccessListener {
                // Actualiza la lista de usuarios después de agregar uno nuevo
                loadUsers()
            }
            .addOnFailureListener { e ->
                // Maneja errores de agregar usuario
            }
    }

}