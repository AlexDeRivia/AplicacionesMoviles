# 🎮 GameVault - Firebase Authentication Android

Una aplicación Android moderna con **Firebase Authentication** y tema gaming completo. Perfecta para aprender autenticación móvil con una interfaz atractiva de videojuegos.

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%230095D5.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-039BE5?style=for-the-badge&logo=Firebase&logoColor=white)

## 📱 Características

### 🔐 **Autenticación Completa**
- ✅ Login/Registro con email y contraseña
- ✅ Login anónimo (modo invitado)
- ✅ Recuperación de contraseña
- ✅ Verificación de email
- ✅ Validaciones y manejo de errores
- ✅ Logout seguro

### 🎨 **Tema Gaming**
- 🌈 Paleta de colores neón (púrpura, cyan, verde)
- 🎮 Iconos gaming personalizados
- 🌙 Modo oscuro con gradientes
- ✨ Efectos visuales atractivos
- 🎯 Interfaz completamente en español

### 🏗️ **Arquitectura**
- 📱 Material Design 3
- 🏛️ MVVM pattern ready
- 🔧 Kotlin moderno
- 🚀 Firebase SDK actualizado

## 🚀 Instalación y Configuración

### 1️⃣ **Fork del Proyecto**

```bash
# 1. Haz fork de este repositorio en GitHub
# 2. Clona tu fork
git clone https://github.com/TU_USUARIO/app_s10.git
cd app_s10
```

### 2️⃣ **Configurar Firebase**

#### **Crear Proyecto Firebase:**
1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. Haz clic en "Crear un proyecto"
3. Ingresa el nombre: `GameVault` (o el que prefieras)
4. Habilita Google Analytics (opcional)
5. Crea el proyecto

#### **Agregar App Android:**
1. En la consola de Firebase, haz clic en "Agregar app" → Android
2. **Nombre del paquete:** `com.example.app_s10`
3. **Nombre de la app:** `GameVault`
4. **Certificado SHA-1:** (opcional por ahora)
5. Descarga el archivo `google-services.json`

#### **Reemplazar archivo de configuración:**
```bash
# Reemplaza el archivo placeholder con tu archivo real
cp ruta/a/tu/google-services.json app/google-services.json
```

### 3️⃣ **Habilitar Authentication**

1. En Firebase Console, ve a **Authentication**
2. Haz clic en **Sign-in method**
3. Habilita estos proveedores:
   - ✅ **Correo electrónico/contraseña**
   - ✅ **Anónimo** (opcional, para modo invitado)

### 4️⃣ **Abrir en Android Studio**

```bash
# Abre Android Studio
# File → Open → Selecciona la carpeta app_s10
# Espera a que Gradle sincronice
```

### 5️⃣ **Ejecutar la App**

1. Conecta tu dispositivo Android o inicia un emulador
2. Haz clic en **Run** ▶️
3. ¡La app se instalará y abrirá!

## 📁 Estructura del Proyecto

```
app_s10/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/app_s10/
│   │   │   ├── LoginActivity.kt      # 🔐 Pantalla de login
│   │   │   └── MainActivity.kt       # 🏠 Dashboard principal
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_login.xml    # 🎨 UI Login
│   │   │   │   └── activity_main.xml     # 🎨 UI Dashboard
│   │   │   ├── values/
│   │   │   │   ├── colors.xml        # 🌈 Colores gaming
│   │   │   │   └── strings.xml       # 🇪🇸 Textos en español
│   │   │   └── drawable/             # 🎮 Iconos y fondos
│   │   └── AndroidManifest.xml       # ⚙️ Configuración app
│   ├── google-services.json          # 🔥 Config Firebase
│   └── build.gradle.kts              # 📦 Dependencias
└── README.md                         # 📖 Este archivo
```

## 🎮 Cómo Usar la App

### **1. Primera Vez**
- Abre la app
- Verás la pantalla de login con tema gaming
- Haz clic en "REGISTRARSE" para crear una cuenta

### **2. Registro**
- Ingresa tu email y contraseña (mín. 6 caracteres)
- Haz clic en "REGISTRARSE"
- Se enviará un email de verificación (opcional)

### **3. Login**
- Ingresa tus credenciales
- Haz clic en "INICIAR SESIÓN"
- Accederás al dashboard gaming

### **4. Modo Invitado**
- Haz clic en "Continuar como invitado"
- Acceso instantáneo sin registro

### **5. Dashboard**
- Ve tu información de usuario
- Explora las secciones gaming
- Cierra sesión cuando quieras

## 🎮 **PROYECTO AVANZADO: Sistema de Registro de Juegos**

### 📋 **Objetivo del Proyecto**
Ahora que tienes la **autenticación Firebase** funcionando, vamos a crear un **sistema completo de registro de juegos** usando **Firebase Realtime Database**. Los usuarios podrán:

- ✅ **Registrar nuevos juegos** con información detallada
- ✅ **Ver lista de juegos registrados** en tiempo real
- ✅ **Editar y eliminar** sus propios juegos
- ✅ **Filtrar juegos** por categoría y rating
- ✅ **Sincronización automática** con Firebase

### 🔥 **PASO 1: Configurar Firebase Realtime Database**

#### **1️⃣ Habilitar Realtime Database**
1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. Selecciona tu proyecto **GameVault**
3. En el menú lateral, haz clic en **"Realtime Database"**
4. Haz clic en **"Crear base de datos"**
5. Selecciona **"Comenzar en modo de prueba"** (para desarrollo)
6. Escoge tu región (recomendado: **us-central1**)

#### **2️⃣ Configurar Reglas de Seguridad**
```json
{
  "rules": {
    "games": {
      "$uid": {
        ".read": "$uid === auth.uid",
        ".write": "$uid === auth.uid"
      }
    }
  }
}
```

#### **3️⃣ Agregar Dependencia**
En `app/build.gradle.kts`, agrega:
```kotlin
dependencies {
    // ... dependencias existentes
    implementation("com.google.firebase:firebase-database:20.3.0")
}
```

### 🎯 **PASO 2: Crear Modelo de Datos**

#### **1️⃣ Crear clase Game.kt**
```kotlin
// app/src/main/java/com/example/app_s10/Game.kt
data class Game(
    val id: String = "",
    val title: String = "",
    val genre: String = "",
    val platform: String = "",
    val rating: Float = 0f,
    val description: String = "",
    val releaseYear: Int = 0,
    val completed: Boolean = false,
    val userId: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
```

### 🎨 **PASO 3: Crear Interfaz de Registro**

#### **1️⃣ Crear activity_add_game.xml**
```xml
<!-- app/src/main/res/layout/activity_add_game.xml -->
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/gaming_gradient_bg">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="24dp">

        <!-- Título -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="🎮 Registrar Nuevo Juego"
            android:textSize="28sp"
            android:textColor="@color/gaming_cyan"
            android:textStyle="bold"
            android:gravity="center"
            android:layout_marginBottom="32dp" />

        <!-- Formulario -->
        <com.google.android.material.card.MaterialCardView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:cardCornerRadius="16dp"
            app:cardElevation="8dp"
            android:layout_marginBottom="24dp">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:orientation="vertical"
                android:padding="24dp">

                <!-- Campo Título -->
                <com.google.android.material.textfield.TextInputLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:hint="Título del juego"
                    android:layout_marginBottom="16dp">
                    
                    <com.google.android.material.textfield.TextInputEditText
                        android:id="@+id/etGameTitle"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content" />
                </com.google.android.material.textfield.TextInputLayout>

                <!-- Campo Género -->
                <com.google.android.material.textfield.TextInputLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:hint="Género"
                    android:layout_marginBottom="16dp">
                    
                    <com.google.android.material.textfield.TextInputEditText
                        android:id="@+id/etGameGenre"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content" />
                </com.google.android.material.textfield.TextInputLayout>

                <!-- Rating -->
                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="Rating: 0/5"
                    android:textSize="16sp"
                    android:layout_marginBottom="8dp" />

                <RatingBar
                    android:id="@+id/ratingBar"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:numStars="5"
                    android:rating="0"
                    android:layout_marginBottom="16dp" />

                <!-- Botón Guardar -->
                <Button
                    android:id="@+id/btnSaveGame"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="💾 GUARDAR JUEGO"
                    android:backgroundTint="@color/gaming_purple"
                    android:textColor="@android:color/white"
                    android:textStyle="bold"
                    android:layout_marginTop="16dp" />

            </LinearLayout>
        </com.google.android.material.card.MaterialCardView>

    </LinearLayout>
</ScrollView>
```

### 📱 **PASO 4: Crear Activity para Registro**

#### **1️⃣ Crear AddGameActivity.kt**
```kotlin
// app/src/main/java/com/example/app_s10/AddGameActivity.kt
class AddGameActivity : AppCompatActivity() {
    
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)
        
        // Inicializar Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        
        setupViews()
    }
    
    private fun setupViews() {
        val etTitle = findViewById<TextInputEditText>(R.id.etGameTitle)
        val etGenre = findViewById<TextInputEditText>(R.id.etGameGenre)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val btnSave = findViewById<Button>(R.id.btnSaveGame)
        
        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val genre = etGenre.text.toString().trim()
            val rating = ratingBar.rating
            
            if (validateInput(title, genre)) {
                saveGame(title, genre, rating)
            }
        }
    }
    
    private fun validateInput(title: String, genre: String): Boolean {
        if (title.isEmpty()) {
            showError("El título es obligatorio")
            return false
        }
        if (genre.isEmpty()) {
            showError("El género es obligatorio")
            return false
        }
        return true
    }
    
    private fun saveGame(title: String, genre: String, rating: Float) {
        val userId = auth.currentUser?.uid ?: return
        val gameId = database.child("games").child(userId).push().key ?: return
        
        val game = Game(
            id = gameId,
            title = title,
            genre = genre,
            rating = rating,
            userId = userId
        )
        
        database.child("games").child(userId).child(gameId).setValue(game)
            .addOnSuccessListener {
                showSuccess("¡Juego guardado exitosamente!")
                finish()
            }
            .addOnFailureListener { exception ->
                showError("Error al guardar: ${exception.message}")
            }
    }
    
    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    
    private fun showSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
```

### 📋 **PASO 5: Crear Lista de Juegos**

#### **1️⃣ Crear item_game.xml**
```xml
<!-- app/src/main/res/layout/item_game.xml -->
<com.google.android.material.card.MaterialCardView 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cardCornerRadius="12dp"
    app:cardElevation="4dp"
    android:layout_margin="8dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp">

        <TextView
            android:id="@+id/tvGameTitle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Título del Juego"
            android:textSize="18sp"
            android:textStyle="bold"
            android:textColor="@color/gaming_purple" />

        <TextView
            android:id="@+id/tvGameGenre"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Género"
            android:textSize="14sp"
            android:layout_marginTop="4dp" />

        <RatingBar
            android:id="@+id/ratingBarItem"
            style="?android:attr/ratingBarStyleSmall"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp" />

    </LinearLayout>
</com.google.android.material.card.MaterialCardView>
```

#### **2️⃣ Crear GameAdapter.kt**
```kotlin
// app/src/main/java/com/example/app_s10/GameAdapter.kt
class GameAdapter(private var games: List<Game>) : 
    RecyclerView.Adapter<GameAdapter.GameViewHolder>() {
    
    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvGameTitle)
        val tvGenre: TextView = itemView.findViewById(R.id.tvGameGenre)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBarItem)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.tvTitle.text = game.title
        holder.tvGenre.text = game.genre
        holder.ratingBar.rating = game.rating
    }
    
    override fun getItemCount() = games.size
    
    fun updateGames(newGames: List<Game>) {
        games = newGames
        notifyDataSetChanged()
    }
}
```

### 🔄 **PASO 6: Integrar con MainActivity**

#### **1️⃣ Actualizar MainActivity.kt**
```kotlin
// Agregar en MainActivity.kt
private fun setupGameFeatures() {
    // Botón para agregar juego
    val btnAddGame = findViewById<Button>(R.id.btnAddGame)
    btnAddGame.setOnClickListener {
        startActivity(Intent(this, AddGameActivity::class.java))
    }
    
    // Botón para ver juegos
    val btnViewGames = findViewById<Button>(R.id.btnViewGames)
    btnViewGames.setOnClickListener {
        startActivity(Intent(this, GamesListActivity::class.java))
    }
}
```

### 🎯 **PASO 7: Tareas para los Estudiantes**

#### **📝 Ejercicios Obligatorios:**

1. **🔥 Configuración Firebase Database**
   - Habilitar Realtime Database en Firebase Console
   - Configurar reglas de seguridad
   - Agregar dependencia en build.gradle

2. **💾 Implementar Registro de Juegos**
   - Crear modelo Game.kt
   - Implementar AddGameActivity.kt
   - Crear formulario con validaciones

3. **📋 Lista de Juegos**
   - Crear RecyclerView con GameAdapter
   - Implementar lectura en tiempo real
   - Mostrar datos del usuario autenticado

4. **✨ Funcionalidades Extra (Opcional)**
   - Editar juegos existentes
   - Eliminar juegos
   - Filtros por género
   - Búsqueda por título

#### **🎮 Resultado Esperado:**
- Los usuarios pueden registrar sus juegos favoritos
- Los datos se guardan automáticamente en Firebase
- La lista se actualiza en tiempo real
- Solo ven sus propios juegos (privacidad)

### 📚 **Recursos de Apoyo**

- **[Firebase Realtime Database Docs](https://firebase.google.com/docs/database/android/start)**
- **[RecyclerView Tutorial](https://developer.android.com/develop/ui/views/layout/recyclerview)**
- **[Material Design Components](https://material.io/develop/android)**

---

## 🛠️ Personalización

### **Cambiar Colores**
Edita `app/src/main/res/values/colors.xml`:
```xml
<color name="gaming_purple">#TU_COLOR</color>
<color name="gaming_cyan">#TU_COLOR</color>
```

### **Cambiar Textos**
Edita `app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">Tu App Name</string>
<string name="login_title">Tu Título</string>
```

### **Agregar Funcionalidades**
- Edita `MainActivity.kt` para agregar más features
- Crea nuevas Activities para pantallas adicionales
- Integra Firestore para guardar datos de usuario

## 🔧 Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Kotlin** | 2.0.21 | Lenguaje principal |
| **Android Gradle Plugin** | 8.9.2 | Build system |
| **Firebase BOM** | 33.7.0 | Gestión de versiones Firebase |
| **Firebase Auth** | Latest | Autenticación |
| **Material Design** | 1.12.0 | Componentes UI |
| **ConstraintLayout** | 2.2.1 | Layouts responsive |

## 🚨 Solución de Problemas

### **Error: "google-services.json not found"**
```bash
# Asegúrate de que el archivo esté en la ubicación correcta
ls app/google-services.json
# Si no existe, descárgalo desde Firebase Console
```

### **Error: "Default FirebaseApp is not initialized"**
- Verifica que `google-services.json` sea válido
- Asegúrate de que el package name coincida: `com.example.app_s10`
- Limpia y reconstruye el proyecto: Build → Clean Project

### **Error de autenticación**
- Verifica que Email/Password esté habilitado en Firebase Console
- Revisa la conexión a internet
- Verifica las reglas de seguridad de Firebase

### **Problemas de compilación**
```bash
# Limpia el proyecto
./gradlew clean

# Reconstruye
./gradlew build
```

## 🤝 Contribuir

1. **Fork** este repositorio
2. **Crea** una rama para tu feature: `git checkout -b mi-nueva-feature`
3. **Commit** tus cambios: `git commit -am 'Agregar nueva feature'`
4. **Push** a la rama: `git push origin mi-nueva-feature`
5. **Crea** un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ve el archivo [LICENSE](LICENSE) para más detalles.

## 👨‍💻 Autor

**GxJohan**
- GitHub: [@GxJohan](https://github.com/GxJohan)
- Proyecto: [app_zone_app](https://github.com/GxJohan/app_zone_app)

## 🌟 ¿Te gustó el proyecto?

¡Dale una ⭐ al repositorio si te sirvió! Ayuda a otros developers a encontrarlo.

## 📚 Recursos Adicionales

- [📖 Documentación Firebase Auth](https://firebase.google.com/docs/auth/android/start)
- [🎨 Material Design Guidelines](https://material.io/design)
- [📱 Android Developer Guide](https://developer.android.com/guide)
- [🔥 Firebase Console](https://console.firebase.google.com/)

---

### 🎮 **¡Happy Gaming & Coding!** 🎮

> Desarrollado con ❤️ para la comunidad de developers Android