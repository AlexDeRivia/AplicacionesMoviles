# 🔧 Configuración de Firebase

## ⚠️ IMPORTANTE: Archivo google-services.json

El archivo `google-services.json` incluido es un **PLACEHOLDER/EJEMPLO** y debe ser reemplazado con tu archivo real de Firebase.

### 📱 Cómo obtener tu google-services.json real:

1. Ve a la [Consola de Firebase](https://console.firebase.google.com/)
2. Crea un nuevo proyecto o selecciona uno existente
3. Agrega una aplicación Android
4. Usa el nombre del paquete: `com.example.app_s10`
5. Descarga el archivo `google-services.json`
6. Reemplaza el archivo actual en la carpeta `app/`

### 🎮 Configuración de Authentication:

1. En la consola de Firebase, ve a **Authentication**
2. Habilita los métodos de inicio de sesión que desees:
   - Email/Contraseña ✅ (recomendado para videojuegos)
   - Google (opcional)
   - Anónimo (opcional para invitados)

### 🔐 Reglas de seguridad recomendadas:

```javascript
// Firestore Database Rules
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
  }
}
```

¡Tu aplicación estará lista para autenticación una vez reemplaces el archivo!