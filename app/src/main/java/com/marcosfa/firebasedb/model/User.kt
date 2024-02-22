package com.marcosfa.firebasedb.model


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf



/**
 * This class determines the parameters a User in Our database should have
 * For FIREBASE must have an empty contstructor
 */
data class User (
    val name:String,
    val age:String,
    val gmail:String,
    val connected:Boolean

){
    //Here we declare the empty constriuctor
    constructor():this("","","@gmail.com",false)
}
val TAG = "Firebase"
val TAG2 = "FirebaseID"

object DataUser {

    val name = mutableStateOf("")
    val age = mutableStateOf("")
    val gmail = mutableStateOf("")
    val password = mutableStateOf("")
    val users = mutableStateOf<List<User>>(emptyList())
    val userConnected = mutableStateOf<User?>(null)
    var state : MutableState<State> = mutableStateOf( State.REGISTRO)
    val currentID = mutableStateOf("")
    val gmailUsuarioAEliminar = mutableStateOf("")
    val passwordUsuarioEliminar = mutableStateOf("")


    /**
     * Enumeración que representa los posibles estados de la aplicación.
     *
     * Puede tomar uno de los siguientes valores:
     * - [REGISTRO]: Estado de registro de usuario.
     * - [LOGIN]: Estado de inicio de sesión.
     * - [HOME]: Estado de la pantalla principal (inicio de sesión exitoso).
     *
     * Esta enumeración se utiliza para gestionar el flujo de estados en la aplicación.
     */
    enum class State{
        REGISTRO,
        LOGIN,
        HOME,
        ADMIN
    }


}
