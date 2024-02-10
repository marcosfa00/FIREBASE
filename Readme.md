
# PROGRAMANDO CON MVVM & FIREBASE

Model viewModel View

Bien una vez configurada la base de datos y descargado el archivo **json** ya podemos empezar a programar.

las estructura de datos sera la siguiente:



```mermaid
graph TD
    subgraph Firebase
        A(User) -->|DataClass| B(UserData)
        A --> C{Repository}
        C -->|Singleton| D[Repository]
        D -->|Methods| E(GetData, UpdateData, ...)
    end

    subgraph ViewModel
        F[ViewModel] -->|Dependency Injection| D
        F -->|Methods| G(ProcessData, DisplayData, ...)
    end

    subgraph Views
        H[Registration] -->|Handles User Registration| F
        I[Login] -->|Handles User Login| F
        J[Show] -->|Displays Users| F
        K[Update] -->|Updates Users| F
    end

``````
## REGISTRO USUARIOS
Bien a continuación veremos como simplemente registrar usuarios en la base de datos, (
Posteriormente lo cambiemos por un Listener, pero es importante entender la logica primero) 

Tendremos una paquete **MODEL** donde estará nuestra clase **USER** que constará de una **data class** esta recogerá todos los datos
que se guardarán en la base de datos. 

```KOTLIN

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
```
**IMPORTANTE: ** Para trabajar con Firebase debemos crear un contructor por defecto de esta clase

TENDREMOS TAMBIÉN UN **Object** de tipo **DataUser** que este si será el engargado de recoger todos los datos de la UI para a traves del **VIEWMODEL**
pasarselos a la base de datos:

```KOTLIN

object DataUser {
    val id = mutableStateOf("")
    val name = mutableStateOf("")
    val age = mutableStateOf("")
    val gmail = mutableStateOf("")
    val users = mutableStateOf<List<User>>(emptyList())

}
```
EN LA CLASE REPOSITORY ES LA QUE REALMENTE ACTUARÁ COMO **MODEL** Y DE LA CUAL OBTENDREMOS LAS FUNCIONES DE ACCESO A LA BASE DE DATOS

```KOTLIN

/**
 * Here there are the Methods to connect the database ans obtain all the data
 * as well as uploading data
 */
object repository {
    //It works as a singleton so it must be created an instance


    private val database = FirebaseFirestore.getInstance()

    fun addUser(user: User): Task<DocumentReference> { //Task indica que devuelkve una operacion ASINCRONA, la referencia de iun documenrto
        return database.collection("users").add(user)
    }

    fun getUsers(): Task<QuerySnapshot> {
        // Realiza una consulta para obtener todos los documentos de la colección "usuarios"
        return database.collection("users").get()
    }
}
```

**VIEWMODEL**

Por inyección de dependencias obtendremos todos los datos de la clase **REPOSITORY**
```KOTLIN
//To work directly9 with the repository Methods the injection of dependences is obligatory in MVVM design Patron
class myViewModel(private val model: repository ): ViewModel() {


    /**
     * Al inicializar el viewModel en el MainActivity.kt
     * se cargará las funciones que llamemos desde el init
     */
    init {
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

                DataUser.users.value = userList
                Log.d(TAG, "Users loaded successfully: ${userList.size} users")
                for (i in 0 until userList.size) {
                    Log.d(
                        TAG,
                        "Id:${userList.get(i).id}  Nombre: ${userList.get(i).name}, Edad: ${
                            userList.get(i).age
                        }, Email: ${userList.get(i).gmail}"
                    )
                }
                Log.d(TAG, "MI LISTA DE USUARIOS DEL DATA USER")


            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error loading users", e)
            }
    }
}

```



## LISTENER
Bien hemos substituido el código Anterior para hacer una función que sea un Listener,
Esta nos permitirá actualizar los cambios cada Rato, por lo que si desde un dispositivo se crea un nuevo usuario
desde otro dispositivo se actualizará el usuario

Para ello en la clase Model se ha substituido la Función Antigua **getUsers** por un Listener:


esta función crea un flujo que emite listas de usuarios cada vez que hay un cambio en la colección "users" de Firestore. Además, se encarga de manejar errores y cerrar adecuadamente el flujo cuando sea necesario.

**FUNCION EN EL MODEL**

```Kotlin
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
```
**FUNCION EN EL VIEWMODEL**

Bien para que la funcion anterior tenga Efecto, debemos crear una instancia de la misma en el **viewModel** de manera que
pudamos realmente estar escuchando todo el rato de la base de datos.

EN la siguiente funcion creamos una corrutina que nos brinda **viewModel** esto hace que el código funcione
de manera Asincrona y no se nos pare la aplicacionc ada vez que se accede a la base de datos.

Tras obtener todos los datos llamando a la función del **MODEL**  guardamos la lista de usuarios obtenidos en 
Un **MutableState de tipo Lista de Users** para poder acceder a estos datos mas tarde.

Esta función se llamará en la inicialización del **viewModel**

```kotlin
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

```

```mermaid
   %%{init: { 'logLevel': 'debug', 'theme': 'base', 'gitGraph': {'showBranches': true, 'showCommitLabel': true, 'mainBranchName': 'Main'}} }%%
gitGraph
  commit id:"Readme.md"
  commit id:"readmeFiles/"
  branch dev
    commit id:"MVVM + FIREBASE + REGISTER"
    commit id: "GOOGLE.json + build.gradle.kts"
    commit id: "README EXPLANATION + GITGRAPH"
    commit id: "LAST UPDATE"
    commit id: "Add Users to DataUser & show the list on the Log CAT"
    commit id: "Se muestran los Usuarios"
  branch Listener
    commit id: "Listener"
    commit id: "Readme updated"
    checkout dev
    merge Listener
    checkout Main
    merge dev
   
```






