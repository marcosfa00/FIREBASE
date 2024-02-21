package com.marcosfa.firebasedb.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.marcosfa.firebasedb.model.DataUser
import com.marcosfa.firebasedb.model.TAG
import com.marcosfa.firebasedb.model.TAG2
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
     * Función para agregar un usuario utilizando Firebase Authentication.
     *
     * @param user Objeto User con la información del usuario a agregar.
     * @param autentificacion Objeto FirebaseAuth para la autenticación.
     */
    fun addUser(user: User, autentificacion: FirebaseAuth) {
        autenticarUsuario(user.gmail, DataUser.password.value,autentificacion, user)


    }
    /**
     * Función para autenticar un usuario con correo y contraseña utilizando Firebase Authentication.
     *
     * @param mail Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @param autentificacion Objeto FirebaseAuth para la autenticación.
     * @param user Objeto User con la información del usuario.
     */
    fun autenticarUsuario(mail:String, password :String, autentificacion :FirebaseAuth, user: User){
        autentificacion.createUserWithEmailAndPassword(mail,password)
            .addOnCompleteListener{
                    task ->
                if (task.isSuccessful) {
                    Log.d(TAG2, "Usuario Autenticado ${autentificacion.currentUser?.uid}")
                    model.addUser(user, autentificacion.currentUser?.uid.toString())
                        .addOnSuccessListener {
                            // Actualiza la lista de usuarios después de agregar uno nuevo
                            listenForUserChanges()


                        }
                        .addOnFailureListener { e ->
                            // Maneja errores de agregar usuario
                            Log.d(TAG,"Error $e")
                        }

                    // Aquí puedes llamar a tu lógica para agregar el usuario a la base de datos
                } else {
                    Log.d(TAG2, "Error en la autenticación: ${task.exception}")
                }
            }


    }


    /**
     * Función para iniciar sesión de un usuario mediante el correo electrónico y la contraseña.
     *   @param mail Correo electrónico del usuario.
     *   @param password Contraseña del usuario.
     *   @param autentificacion Instancia de FirebaseAuth utilizada para la autenticación.
     *   */
    fun loginUser(mail: String,password: String,autentificacion: FirebaseAuth){
        autentificacion.signInWithEmailAndPassword(mail,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    DataUser.currentID.value = autentificacion.currentUser?.uid.toString()
                    Log.d(TAG2, "Inicio de sesión exitoso para ${autentificacion.currentUser?.uid}")
                    DataUser.state.value = DataUser.State.HOME
                }else{
                    Log.d(TAG2, "Error en el inicio de sesión: ${task.exception}")
                }
            }
    }


    /**
     * Retrieves user data for the current user based on the provided [mail].
     *
     * This function first updates the user data by invoking [listenForUserChanges], then iterates through
     * the updated user list in [DataUser.users.value] to find the user with the specified email [mail].
     * The user data is then returned.
     *
     * @param mail The email address of the user for whom data is to be retrieved.
     * @return A [User] object containing the data of the user with the specified email, or a default
     * [User] object if the user is not found.
     */
    fun getDataCurrentUser(mail: String):User{
        // actualizamos los datos de los usuarios
        listenForUserChanges()
        var user:User = User()
        for (i in DataUser.users.value){
            if (mail.equals(i.gmail)){
                Log.d(TAG2,"Hemos encontrado al usuario actual $i")
                user = i
            }
        }
        return user
    }



    /**
     * Retrieves user information by their unique identifier [id] using the [model.getUserConnected] method.
     *
     * This function is designed to be called within a [viewModelScope.launch] coroutine scope to perform
     * asynchronous operations. It fetches the user information and logs the result using [Log.d].
     *
     * @param id The unique identifier of the user.
     */
    fun getuserById(id:String) {
        viewModelScope.launch {
            model.getUserConnected(id)
            Log.d(TAG2, DataUser.userConnected.value.toString())
            if (DataUser.userConnected.value?.gmail.equals("marcos@gmail.com")){
                DataUser.state.value = DataUser.State.ADMIN
            }else{
                DataUser.state.value = DataUser.State.HOME
            }
        }
    }

    /**
     * Deletes a user from the model based on their email address [gmail].
     *
     * This function is designed to be called within a [viewModelScope.launch] coroutine scope to perform
     * asynchronous operations. It deletes the user using the [model.deleteUser] method.
     *
     * @param gmail The email address of the user to be deleted.
     */
    fun deleteUser(gmail: String){
        viewModelScope.launch {
            model.deleteUser(gmail)
        }
    }


    /**
     * Updates the age of the current user with the provided [age].
     *
     * This function is designed to be called within a [viewModelScope.launch] coroutine scope to perform
     * asynchronous operations. It updates the user's age using the [model.updateAgeUser] method, retrieves
     * the updated user information, and logs a message indicating that the age has been updated.
     *
     * @param age The new age value to be assigned to the current user.
     */
    fun updateAge(age: String){
        viewModelScope.launch {
            model.updateAgeUser(DataUser.currentID.value,age)
            getuserById(DataUser.currentID.value)

            Log.d(TAG2,"Edad Acualizada")
        }
    }

















}