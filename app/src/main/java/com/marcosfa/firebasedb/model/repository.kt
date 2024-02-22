package com.marcosfa.firebasedb.model

import android.annotation.SuppressLint
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
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
    fun addUser(user: User, userId: String): Task<Void> { //Task indica que devuelkve una operacion ASINCRONA, la referencia de iun documenrto
        val userDocument = database.collection("users").document(userId)
        Log.d(TAG2,userId)
        return userDocument.set(user)
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
     * Creates a callback flow that listens for changes in the "users" collection in the Firestore database.
     *
     * This function uses a callback flow to continuously monitor changes in the Firestore database
     * "users" collection. It emits a list of [User] objects whenever there is a change in the collection.
     *
     * @return A callback flow that emits a list of [User] objects whenever there is a change in the "users" collection.
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


    /**
     * Retrieves user information from the Firestore database based on the provided [id].
     *
     * This function queries the "users" collection in the Firestore database using the [id]. If the
     * query is successful, it converts the Firestore document to a [User] object and updates the
     * [DataUser.userConnected] LiveData with the retrieved user information. If the query fails,
     * it sets the [DataUser.userConnected] LiveData to null.
     *
     * @param id The unique identifier of the user to retrieve from the Firestore database.
     */
    fun getUserConnected(id: String) {
        database.collection("users").document(id).get().addOnSuccessListener { document ->
            val user = document?.toObject(User::class.java)
            DataUser.userConnected.value = user
            Log.d(TAG2, "user by id $id = ${user.toString()}")
        }.addOnFailureListener {
            DataUser.userConnected.value = null
        }
    }


    fun deleteUser(gmail:String){
        val usersCollection =  database.collection("users")
        val query = usersCollection.whereEqualTo("gmail",gmail)
        query.get().addOnSuccessListener{querySnapshot ->
            //verificamos si se encontraron documentos:
            if (!querySnapshot.isEmpty) {
                // Itera sobre los documentos y elimina cada uno
                for (document in querySnapshot.documents) {
                    document.reference.delete().addOnSuccessListener {
                        // Documento eliminado con éxito
                        Log.d(TAG2, "Usuario con correo $gmail eliminado exitosamente.")
                    }.addOnFailureListener { exception ->
                        // Manejo de errores al intentar eliminar el documento
                        Log.e(TAG2, "Error al eliminar usuario con correo $gmail", exception)
                    }
                }
            } else {
                // No se encontraron documentos con el correo proporcionado
                Log.d(TAG2, "No se encontraron usuarios con correo $gmail.")
            }

        }.addOnFailureListener{exception ->
            Log.e(TAG2, "Error al consultar usuarios por correo $gmail", exception)

        }
    }








    fun getDocumentIdByField(gmail:String, onComplete: (String?) -> Unit) {

        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("users")

        collectionRef.whereEqualTo("gmail", gmail)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // Encontró documentos que coinciden con el campo y valor proporcionados
                    val documentId = querySnapshot.documents[0].id
                    Log.d(TAG2,"El id del usuario a eliminar es $documentId")
                    onComplete(documentId)
                } else {
                    // No se encontraron documentos que coincidan
                    Log.d(TAG2,"NO SE HA OBTEMNIDO EL ID")
                    onComplete(null)
                }
            }
            .addOnFailureListener { exception ->
                // Manejo de errores al consultar la colección
                onComplete(null)
            }
    }






    /**
     * Actualiza el campo "age" de un usuario en la colección "users" de Firestore.
     *
     * Esta función toma el UID del usuario y el nuevo valor del campo "age", y utiliza el método
     * [update] para aplicar la actualización al documento correspondiente en la colección "users".
     * La actualización se refleja en el registro de la consola y en caso de éxito o fracaso.
     *
     * @param uid El UID único del usuario cuyo campo "age" se va a actualizar.
     * @param age El nuevo valor que se asignará al campo "age" del usuario.
     */
    fun updateAgeUser(uid: String, age: String) {
        // Referencia a la colección "users" en Firestore
        val userCollection = database.collection("users")

        // Crea un mapa con el campo a actualizar y su nuevo valor
        val updates = hashMapOf<String, Any>(
            "age" to age
        )

        // Actualiza el documento con el nuevo valor
        userCollection.document(uid)
            .update(updates)
            .addOnSuccessListener {
                // Actualización exitosa

                Log.d(TAG, "Gmail del usuario con UID $uid actualizado a $age.")
            }
            .addOnFailureListener { exception ->
                // Manejo de errores al intentar actualizar el documento
                Log.e(TAG, "Error al actualizar el gmail del usuario con UID $uid", exception)
            }
    }




}