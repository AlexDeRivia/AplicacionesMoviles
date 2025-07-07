# 🤝 Contribuir a GameVault

¡Gracias por tu interés en contribuir a GameVault! Este documento te guiará paso a paso para hacer tu primera contribución.

## 🚀 Cómo Contribuir

### 1️⃣ **Fork del Repositorio**

```bash
# 1. Ve a GitHub y haz clic en "Fork" en el repositorio original
# 2. Clona TU fork (no el original)
git clone https://github.com/TU_USUARIO/app_s10.git
cd app_s10

# 3. Agrega el repositorio original como remote
git remote add upstream https://github.com/USUARIO_ORIGINAL/app_s10.git
```

### 2️⃣ **Configurar el Entorno**

```bash
# 1. Asegúrate de tener Android Studio instalado
# 2. Abre el proyecto en Android Studio
# 3. Configura tu Firebase (sigue las instrucciones del README.md)
# 4. Ejecuta la app para verificar que funciona
```

### 3️⃣ **Crear una Rama para tu Feature**

```bash
# Mantén tu fork actualizado
git fetch upstream
git checkout main
git merge upstream/main

# Crea una nueva rama
git checkout -b mi-nueva-feature

# Ejemplos de nombres de ramas:
# feature/dark-theme
# fix/login-validation
# improvement/ui-animations
# docs/installation-guide
```

### 4️⃣ **Hacer tus Cambios**

#### **📝 Tipos de Contribuciones Bienvenidas:**

- 🐛 **Bug Fixes**: Corregir errores
- ✨ **Features**: Nuevas funcionalidades
- 🎨 **UI/UX**: Mejoras de interfaz
- 📚 **Documentación**: Mejorar guías y ejemplos
- 🔧 **Refactoring**: Limpiar código
- 🧪 **Tests**: Agregar pruebas unitarias
- 🌍 **Localización**: Traducir a otros idiomas

#### **📋 Estándares de Código:**

```kotlin
// ✅ Usa nombres descriptivos
private fun authenticateUser(email: String, password: String) {
    // Código aquí
}

// ✅ Comenta funciones complejas
/**
 * Valida el formato del email ingresado por el usuario
 * @param email Email a validar
 * @return true si es válido, false si no
 */
private fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

// ✅ Usa constantes para strings repetidos
companion object {
    private const val TAG = "LoginActivity"
    private const val MIN_PASSWORD_LENGTH = 6
}
```

### 5️⃣ **Commit tus Cambios**

```bash
# Agrega los archivos modificados
git add .

# Commit con mensaje descriptivo
git commit -m "feat: agregar validación de email en tiempo real

- Implementar validación mientras el usuario escribe
- Mostrar error inmediato si el formato es incorrecto
- Mejorar UX del formulario de login
- Cerrar #123"

# Ejemplos de prefijos:
# feat: nueva funcionalidad
# fix: corrección de bug
# docs: cambios en documentación
# style: cambios de formato (no afectan lógica)
# refactor: cambios de código (no agregan features ni fixes)
# test: agregar tests
```

### 6️⃣ **Push y Pull Request**

```bash
# Push a tu fork
git push origin mi-nueva-feature

# Ve a GitHub y crea un Pull Request desde tu rama
```

## 📋 Template de Pull Request

Cuando crees tu PR, usa este template:

```markdown
## 📝 Descripción
Breve descripción de los cambios realizados.

## 🎯 Tipo de Cambio
- [ ] 🐛 Bug fix
- [ ] ✨ Nueva feature
- [ ] 🔄 Breaking change
- [ ] 📚 Documentación

## 🧪 Cómo Probar
1. Paso 1 para probar
2. Paso 2 para probar
3. Resultado esperado

## 📱 Screenshots (si aplica)
[Agregar imágenes antes/después]

## ✅ Checklist
- [ ] Mi código sigue las convenciones del proyecto
- [ ] He probado mis cambios localmente
- [ ] He actualizado la documentación si es necesario
- [ ] Mi PR resuelve un issue existente (#123)
```

## 🎯 Ideas de Contribución

### 🔥 **Issues Marcados como "Good First Issue"**
Perfecto para principiantes:
- Agregar más validaciones de formulario
- Mejorar mensajes de error
- Traducir strings a otros idiomas
- Agregar más colores al tema gaming

### 🚀 **Features Avanzadas**
Para developers experimentados:
- Integrar Firestore para perfiles de usuario
- Implementar login con Google
- Agregar biometría (huella dactilar)
- Sistema de notificaciones push
- Modo offline con sincronización

### 🎨 **Mejoras de UI/UX**
- Animaciones entre pantallas
- Splash screen personalizado
- Tema claro/oscuro toggle
- Efectos visuales gaming avanzados
- Responsive design para tablets

## 🛡️ Reportar Bugs

### **Antes de reportar:**
1. Busca si ya existe un issue similar
2. Actualiza a la última versión
3. Prueba en un dispositivo/emulador limpio

### **Template de Bug Report:**

```markdown
**🐛 Descripción del Bug**
Descripción clara del problema.

**📱 Información del Dispositivo**
- Dispositivo: [ej. Samsung Galaxy S21]
- OS: [ej. Android 12]
- Versión de la app: [ej. v1.0.0]

**🔄 Steps to Reproduce**
1. Ir a '...'
2. Hacer clic en '...'
3. Ver error

**✅ Comportamiento Esperado**
Lo que debería pasar.

**❌ Comportamiento Actual**
Lo que está pasando.

**📷 Screenshots**
Si es posible, agregar imágenes.
```

## 📚 Recursos Útiles

- [🎮 Guía de Android Gaming UI](https://developer.android.com/games/develop)
- [🔥 Firebase Auth Documentation](https://firebase.google.com/docs/auth)
- [🎨 Material Design Gaming](https://material.io/design/color/dark-theme.html)
- [📱 Android Kotlin Style Guide](https://developer.android.com/kotlin/style-guide)

## 🏆 Reconocimiento

Todos los contribuidores serán listados en:
- README.md principal
- Sección "Contributors" del proyecto
- Release notes cuando aplicable

## ❓ ¿Preguntas?

- 💬 Abre un issue con la etiqueta "question"
- 📧 Contacta al maintainer
- 🐦 Twitter: [@tu-handle](https://twitter.com/tu-handle)

## 📄 Código de Conducta

Este proyecto sigue el [Contributor Covenant](https://www.contributor-covenant.org/):

- ✅ Sé respetuoso con otros contribuidores
- ✅ Usa lenguaje inclusivo
- ✅ Acepta críticas constructivas
- ✅ Enfócate en lo mejor para la comunidad

---

### 🎮 **¡Gracias por contribuir a GameVault!** 🎮

Tu contribución hace la diferencia para la comunidad de developers Android. 🚀