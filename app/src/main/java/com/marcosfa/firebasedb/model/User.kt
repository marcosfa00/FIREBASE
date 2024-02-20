package com.marcosfa.firebasedb.model


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
    val connected = mutableStateOf(false)
    val userConnected  = mutableStateOf<User?>(null)


}
