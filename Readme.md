

# CONEXIÓN CON BASE DE DATOS FIREBASE
Bien a continuacion vamos a explicar paso a paso como crear una aplicacion en **Android studio** en **Kotlin** en la cual nos conectaremos a una base de datos [FIREBASE](https://firebase.google.com)

## ¿Que base de datos utilizar?

Bien, a la hora de crear iuna **base de datos** en Android, una practica muy común es usar **[ROOM](https://developer.android.com/training/data-storage/room?hl=es-419)** que crea un fichero **SQLite** y guarda los datos en local.

Esto es una forma facil de gaurdar datos, pero no sirve para todo tipo de datos, y lo cierto es que para aplicaciones en las cuales queramos guardar usuarios, no es nada eficiente. Ya que los usuarios que guardes en un dispositivo Android no estarán en otro.

#### Entonces porque se usa ROOM ?

Pues la respuesta es muy sencilla. Room es comunmente utilizado par almacenar datos no triviales en una aplicacion, como la configuración de un usuario.
**Me explico:** Se guardan datos no prescindibles para el funcionamiento de la aplicacion, como si un usuario emplea modo oscuro o claro, si le gusta que los menús se encuentren en una posición u otra, etc.

### FIREBASE
Bien, a la hora de guardar **Usuarios** o datos relevantes d euna aplicación, gaurdarlos en la nube es muy buena practica, ya que todas tus aplicaciones cliente podrás acceder a todos esos datos.



## Configuración

Para poder empezar a usar Firebase debemos preparar Android Studio primero, para ello crearemos una nueva *Empty Activity*

Aquí nos iremos ha los archivos *Gradle* y añadiremos las dependencias de google-services

**build.gradle.kts**

Ruta general:

```kotlin
plugins{
    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.0" apply false
}
    
```

Ruta ./App

```kotlin
plugins{
     // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

dependencies{

// Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    // When using the BoM, you don't specify versions in Firebase library dependencies

    // Add the dependency for the Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics-ktx")

    // TODO: Add the dependencies for any other Firebase products you want to use
    // See https://firebase.google.com/docs/android/setup#available-libraries
    // For example, add the dependencies for Firebase Authentication and Cloud Firestore
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

}

``````

### JSON

Dentro de la ruta **./app** debemos añadir un archivo **.json** que descargaremos de [Firebase](https://firebase.google.com) al crear un nuevo proyecto con una base de datos.

Este debe tener una configuración parecida a lo siguiente:

```json

{
  "project_info": {
    "project_number": "----",
    "project_id": ".....",
    "storage_bucket": "......-.com"
  },
  "client": [
    {
      "client_info": {
        "mobilesdk_app_id": "1:----:android:----",
        "android_client_info": {
          "package_name": "com.----"
        }
      },
      "oauth_client": [],
      "api_key": [
        {
          "current_key": "----"
        }
      ],
      "services": {
        "appinvite_service": {
          "other_platform_oauth_client": []
        }
      }
    },
    {
      "client_info": {
        "mobilesdk_app_id": "1:---:android:----",
        "android_client_info": {
          "package_name": "com.----"
        }
      },
      "oauth_client": [],
      "api_key": [
        {
          "current_key": "----"
        }
      ],
      "services": {
        "appinvite_service": {
          "other_platform_oauth_client": []
        }
      }
    }
  ],
  "configuration_version": "1"
}
``````

---



# Creacion de la base de datos en Firebase


Creamos un nuevo proyecto:

![newProject.png](readmeFiles%2FnewProject.png)

Dentro de este proyecto creamos una nueva base de datos **!Ojo¡** debemos tener en cuenta que esta base de datos no es de tipo **SQL** si no que será una base de datos **documental** como puede ser más conocida: **mongoDB**<img src="https://www.vectorlogo.zone/logos/mongodb/mongodb-icon.svg" alt="mongodb" width="40" height="40"/>

![database.png](readmeFiles%2Fdatabase.png)

Y listo, tras configurar unos sencillos campos ya podemos empezar a utilizar Firebase en nuestro proyecto

