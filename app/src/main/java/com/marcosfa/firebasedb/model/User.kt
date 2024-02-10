package com.marcosfa.firebasedb.model


import androidx.compose.runtime.mutableStateOf



/**
 * This class determines the parameters a User in Our database should have
 * For FIREBASE must have an empty contstructor
 */
data class User (
    val id:String,
    val name:String,
    val age:String,
    val gmail:String

){
    //Here we declare the empty constriuctor
    constructor():this("","","","@gmail.com")
}
val TAG = "Firebase"

object DataUser {
    val id = mutableStateOf("")
    val name = mutableStateOf("")
    val age = mutableStateOf("")
    val gmail = mutableStateOf("")
    val users = mutableStateOf<List<User>>(emptyList())

}
