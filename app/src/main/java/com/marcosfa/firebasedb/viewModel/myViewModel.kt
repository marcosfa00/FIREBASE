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

    /**
     * Inicia la escucha de cambios en la colección de usuarios y actualiza la lista de usuarios en tiempo real.
     *
     * Esta función utiliza [viewModelScope.launch] para lanzar una corrutina en el ámbito de la ViewModel.
     * Dentro de la corrutina, se utiliza el modelo asociado para iniciar la escucha de cambios en la
     * colección de usuarios mediante [model.listenForUserChanges()]. Los cambios en la colección se
     * reciben a través de un flujo y se actualiza la lista de usuarios en el objeto [DataUser.users].
     *
     * Nota: Asegúrese de tener configurada correctamente la función [model.listenForUserChanges()] para
     * obtener un flujo actualizado de la colección de usuarios.
     *
     * Ejemplo de uso:
     * ```
     * listenForUserChanges()
     * ```
     */
    private fun listenForUserChanges() {
        viewModelScope.launch {
            // Escucha cambios en la colección de usuarios
            model.listenForUserChanges().collect { userList ->
                // Actualiza la lista de usuarios
                DataUser.users.value = userList
            }
        }
    }





    /**
     * Agrega un nuevo usuario a la base de datos y maneja las respuestas exitosas y fallidas.
     *
     * Esta función toma un objeto de tipo [User] como parámetro y utiliza el modelo asociado
     * para agregar el usuario a la base de datos. Se adjuntan listeners para manejar tanto el éxito
     * como el fracaso de la operación. En caso de éxito, la lista de usuarios se actualiza llamando
     * a [listenForUserChanges()]. En caso de fracaso, se proporciona una [Exception] que puede
     * ser utilizada para manejar errores relacionados con la adición de usuarios.
     *
     * @param user El objeto [User] que se va a agregar a la base de datos.
     *
     * Ejemplo de uso:
     * ```
     * val newUser = User("nombre", "correo@example.com", ...)
     * addUser(newUser)
     * ```
     */
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