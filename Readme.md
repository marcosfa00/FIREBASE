
# PROGRAMANDO CON MVVM & FIREBASE

Model viewModel View

Bien una vez configurada la base de datos y descargado el archivo **json** ya podemos empezar a programar.

las estructura de datos sera la siguiente:


``` mermaid
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
    branch UI_users
    checkout dev
    commit id: "Se muestran los Usuarios"
   
```






