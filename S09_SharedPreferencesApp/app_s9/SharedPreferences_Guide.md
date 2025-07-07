# Guía de SharedPreferences - App S9

## Descripción General

Esta aplicación implementa una configuración básica de SharedPreferences en Android para almacenar datos de manera persistente en el dispositivo.

## Estructura de la Implementación

### 1. SharedPreferencesHelper.kt

Clase helper que encapsula todas las operaciones de SharedPreferences:

```kotlin
class SharedPreferencesHelper(context: Context)
```

#### Características principales:
- Nombre de preferencias: `AppS9Preferences`
- Modo: `Context.MODE_PRIVATE`
- Métodos para tipos de datos: String, Boolean, Int, Float, Long

#### Métodos disponibles:

| Método | Descripción |
|--------|-------------|
| `saveString(key, value)` | Guarda un valor String |
| `getString(key, defaultValue)` | Obtiene un String guardado |
| `saveBoolean(key, value)` | Guarda un valor Boolean |
| `getBoolean(key, defaultValue)` | Obtiene un Boolean guardado |
| `saveInt(key, value)` | Guarda un valor Int |
| `getInt(key, defaultValue)` | Obtiene un Int guardado |
| `saveFloat(key, value)` | Guarda un valor Float |
| `getFloat(key, defaultValue)` | Obtiene un Float guardado |
| `saveLong(key, value)` | Guarda un valor Long |
| `getLong(key, defaultValue)` | Obtiene un Long guardado |
| `remove(key)` | Elimina una preferencia específica |
| `clearAll()` | Elimina todas las preferencias |
| `contains(key)` | Verifica si existe una clave |

#### Claves predefinidas:
- `KEY_USERNAME`: Para almacenar el nombre de usuario
- `KEY_IS_FIRST_TIME`: Para detectar primera ejecución
- `KEY_USER_ID`: Para almacenar ID de usuario
- `KEY_THEME_MODE`: Para preferencias de tema (para uso futuro)

### 2. MainActivity.kt

Implementación de ejemplo que demuestra el uso de SharedPreferences:

#### Funcionalidades implementadas:
- **Guardar datos**: Almacena nombre de usuario, ID aleatorio y marca de primera vez
- **Cargar datos**: Recupera y muestra los datos guardados
- **Limpiar datos**: Elimina todas las preferencias guardadas
- **Detección de primera ejecución**: Muestra mensaje de bienvenida

### 3. Layout (activity_main.xml)

Interfaz de usuario simple con:
- EditText para ingresar nombre
- Botón "Guardar"
- Botón "Cargar"
- Botón "Limpiar Todo"
- TextView para mostrar resultados

## Cómo Probar la Aplicación

### 1. Compilar la aplicación
```bash
./gradlew assembleDebug
```

### 2. Instalar en dispositivo/emulador
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### 3. Casos de prueba

#### Prueba 1: Primera ejecución
- Al abrir la app por primera vez
- **Resultado esperado**: Toast mostrando "¡Bienvenido por primera vez!"

#### Prueba 2: Guardar datos
1. Escribir un nombre en el campo de texto
2. Presionar "Guardar"
- **Resultado esperado**: Toast "Datos guardados exitosamente"

#### Prueba 3: Cargar datos
1. Presionar "Cargar"
- **Resultado esperado**: Se muestran:
  - Usuario: [nombre guardado]
  - ID: [número aleatorio]
  - Primera vez: No

#### Prueba 4: Persistencia
1. Cerrar completamente la aplicación
2. Volver a abrirla
3. Presionar "Cargar"
- **Resultado esperado**: Los datos siguen disponibles

#### Prueba 5: Limpiar datos
1. Presionar "Limpiar Todo"
- **Resultado esperado**: 
  - Toast "Todas las preferencias han sido eliminadas"
  - Campos vacíos

### 4. Verificar datos guardados (Avanzado)
```bash
adb shell run-as com.example.app_s9 cat /data/data/com.example.app_s9/shared_prefs/AppS9Preferences.xml
```

## Cómo Extender la Funcionalidad

### Agregar nuevos tipos de datos
En `SharedPreferencesHelper.kt`, agregar nuevas claves:
```kotlin
companion object {
    // Agregar nuevas claves
    const val KEY_EMAIL = "email"
    const val KEY_AGE = "age"
}
```

### Uso en otras actividades
```kotlin
// Crear instancia
val prefs = SharedPreferencesHelper(this)

// Guardar
prefs.saveString(SharedPreferencesHelper.KEY_EMAIL, "user@example.com")

// Leer
val email = prefs.getString(SharedPreferencesHelper.KEY_EMAIL)
```

### Implementar preferencias de configuración
```kotlin
// Guardar preferencia de tema
prefs.saveString(SharedPreferencesHelper.KEY_THEME_MODE, "dark")

// Aplicar tema basado en preferencia
when (prefs.getString(SharedPreferencesHelper.KEY_THEME_MODE)) {
    "dark" -> setTheme(R.style.DarkTheme)
    "light" -> setTheme(R.style.LightTheme)
}
```

## Consideraciones de Seguridad

1. **No almacenar información sensible**: SharedPreferences no está encriptado por defecto
2. **Para datos sensibles**: Considerar usar EncryptedSharedPreferences
3. **Permisos**: No se requieren permisos especiales para SharedPreferences

## Ventajas y Limitaciones

### Ventajas
- ✅ Fácil de implementar
- ✅ Persistencia automática
- ✅ No requiere permisos
- ✅ Ideal para configuraciones y preferencias

### Limitaciones
- ❌ No apto para grandes cantidades de datos
- ❌ No es una base de datos relacional
- ❌ Datos en texto plano (no encriptado)
- ❌ Solo tipos de datos primitivos

## Alternativas

Para otros casos de uso, considerar:
- **Room Database**: Para datos estructurados complejos
- **DataStore**: Reemplazo moderno de SharedPreferences
- **EncryptedSharedPreferences**: Para datos sensibles
- **Archivos**: Para datos binarios o grandes

## Recursos Adicionales

- [Documentación oficial de SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences)
- [Migrar a DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
- [EncryptedSharedPreferences](https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences)