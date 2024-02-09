package com.marcosfa.firebasedb.model

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


/**
 * Here there are the Methods to connect the database ans obtain all the data
 * as well as uploading data
 */
object repository {
    //It works as a singleton so it must be created an instance


    private val database = FirebaseFirestore.getInstance()

    fun addUser(user: User): Task<DocumentReference>{ //Task indica que devuelkve una operacion ASINCRONA, la referencia de iun documenrto
        return database.collection("users").add(user)
    }

    fun getUsers(): Task<QuerySnapshot> {
        // Realiza una consulta para obtener todos los documentos de la colección "usuarios"
        return database.collection("users").get()
    }



}