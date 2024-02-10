package com.marcosfa.firebasedb.model

import android.annotation.SuppressLint
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow



/**
 * Here there are the Methods to connect the database ans obtain all the data
 * as well as uploading data
 */
object repository {
    //It works as a singleton so it must be created an instance

    /**
     * Instancia única de la clase [FirebaseFirestore] para acceder a la base de datos Firestore.
     *
     * Esta propiedad privada y estática almacena la única instancia de [FirebaseFirestore]
     * utilizada para interactuar con la base de datos Firestore. Se anota con `@SuppressLint("StaticFieldLeak")`
     * para suprimir advertencias sobre el uso de instancias estáticas en contextos no estáticos.
     * La supresión es necesaria ya que la instancia de [FirebaseFirestore] puede persistir más allá del ciclo
     * de vida de la clase que la contiene, pero en este caso, se controla cuidadosamente para evitar fugas
     * de memoria y problemas relacionados con la recolección de basura.
     *
     * Ejemplo de uso:
     * ```
     * // Acceder a la instancia única de FirebaseFirestore
     * val database = FirebaseFirestore.getInstance()
     * ```
     */
    @SuppressLint("StaticFieldLeak")
    private val database = FirebaseFirestore.getInstance()

    /**
     * Agrega un nuevo usuario a la colección "usuarios" en Firebase Firestore.
     *
     * Esta función toma un objeto de tipo [User] como parámetro y utiliza Firebase Firestore
     * para agregar dicho usuario a la colección. Devuelve un objeto [Task] que representa la
     * operación asíncrona de agregar el usuario, proporcionando la [DocumentReference] del documento
     * recién creado.
     *
     * @param user El objeto [User] que se va a agregar a la colección.
     * @return Un [Task] que representa la operación asíncrona de agregar el usuario.
     * La [DocumentReference] puede ser utilizada para acceder al documento recién creado.
     *
     * Ejemplo de uso:
     * ```
     * val newUser = User("nombre", "correo@example.com", ...)
     * addUser(newUser).addOnSuccessListener { documentReference ->
     *     // Manejar la ejecución exitosa, acceder a la referencia del documento en documentReference
     * }.addOnFailureListener { exception ->
     *     // Manejar la ejecución fallida, acceder a la excepción proporcionada en el parámetro 'exception'
     * }
     * ```
     */
    fun addUser(user: User): Task<DocumentReference>{ //Task indica que devuelkve una operacion ASINCRONA, la referencia de iun documenrto
        return database.collection("users").add(user)
    }

    /**
     * Realiza una consulta para obtener todos los documentos de la colección "usuarios".
     *
     * Esta función utiliza Firebase Firestore para realizar una consulta y devolver un objeto [Task]
     * que representa la operación asíncrona de obtener un [QuerySnapshot].
     *
     * @return Un [Task] que representa la operación asíncrona de obtener un [QuerySnapshot].
     * El [Task] puede ser utilizado para manejar la ejecución exitosa o fallida de la operación.
     *
     * Ejemplo de uso:
     * ```
     * getUsers().addOnSuccessListener { querySnapshot ->
     *     // Manejar la ejecución exitosa, acceder a los documentos en querySnapshot.documents
     * }.addOnFailureListener { exception ->
     *     // Manejar la ejecución fallida, acceder a la excepción proporcionada en el parámetro 'exception'
     * }
     * ```
     */
    fun getUsers(): Task<QuerySnapshot> {
        // Realiza una consulta para obtener todos los documentos de la colección "usuarios"
        return database.collection("users").get()
    }

    /**
     * Creates a callback flow for listening to changes in the "users" collection in a Firestore database.
     */
    fun listenForUserChanges() = callbackFlow<List<User>> {
        // inicializa el listener
        val listener = database.collection("users")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }
                val users = snapshot?.documents?.mapNotNull { document ->
                    document.toObject(User::class.java)
                } ?: emptyList()
                trySend(users).isSuccess
            }
        awaitClose { listener.remove() }
    }




}