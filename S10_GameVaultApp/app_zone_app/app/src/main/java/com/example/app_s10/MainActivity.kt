package com.example.app_s10

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import android.widget.Button



class MainActivity : AppCompatActivity() {
    
    private lateinit var auth: FirebaseAuth
    
    // Views
    private lateinit var tvWelcome: TextView
    private lateinit var tvUserEmail: TextView
    private lateinit var btnLogout: MaterialButton
    private lateinit var cardStats: CardView
    private lateinit var cardAchievements: CardView
    private lateinit var cardProfile: CardView
    private lateinit var cardSettings: CardView
    
    companion object {
        private const val TAG = "MainActivity"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()
        
        // Verificar autenticación
        val currentUser = auth.currentUser
        if (currentUser == null) {
            // Usuario no autenticado, redirigir al login
            redirectToLogin()
            return
        }
        
        // Configurar UI
        setupUI()
        setupWindowInsets()
        
        // Cargar información del usuario
        loadUserInfo(currentUser)
        
        // Configurar listeners
        setupClickListeners()
        setupGameFeatures()
        
        Log.d(TAG, "MainActivity iniciado para usuario: ${currentUser.email}")
    }
    
    private fun setupUI() {
        // Inicializar views
        tvWelcome = findViewById(R.id.tv_welcome)
        tvUserEmail = findViewById(R.id.tv_user_email)
        btnLogout = findViewById(R.id.btn_logout)
        cardStats = findViewById(R.id.card_stats)
        cardAchievements = findViewById(R.id.card_achievements)
        cardProfile = findViewById(R.id.card_profile)
        cardSettings = findViewById(R.id.card_settings)
    }
    
    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    
    private fun loadUserInfo(user: FirebaseUser) {
        // Personalizar saludo según el tipo de usuario
        val welcomeMessage = if (user.isAnonymous) {
            "¡Hola, Invitado!"
        } else {
            "¡Hola, ${user.displayName ?: "Gamer"}!"
        }
        
        tvWelcome.text = welcomeMessage
        
        // Mostrar email o indicar usuario anónimo
        tvUserEmail.text = if (user.isAnonymous) {
            "Usuario invitado"
        } else {
            user.email ?: "Sin email"
        }
        
        // Verificar estado de verificación de email
        if (!user.isAnonymous && user.email != null && !user.isEmailVerified) {
            showEmailVerificationDialog()
        }
    }
    
    private fun setupClickListeners() {
        // Botón logout
        btnLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
        
        // Cards de navegación (placeholder por ahora)
        cardStats.setOnClickListener {
            showFeatureComingSoon("Estadísticas del Jugador")
        }
        
        cardAchievements.setOnClickListener {
            showFeatureComingSoon("Logros")
        }
        
        cardProfile.setOnClickListener {
            showFeatureComingSoon("Perfil")
        }
        
        cardSettings.setOnClickListener {
            showFeatureComingSoon("Configuración")
        }
    }
    
    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Cerrar Sesión")
            .setMessage("¿Estás seguro de que quieres cerrar sesión?")
            .setPositiveButton("Sí") { _, _ ->
                performLogout()
            }
            .setNegativeButton("Cancelar", null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }
    
    private fun performLogout() {
        auth.signOut()
        Toast.makeText(this, getString(R.string.logout_success), Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Usuario desconectado")
        redirectToLogin()
    }
    
    private fun redirectToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    private fun showEmailVerificationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Verificar Email")
            .setMessage(getString(R.string.auth_email_verification_required))
            .setPositiveButton("Enviar verificación") { _, _ ->
                sendEmailVerification()
            }
            .setNegativeButton("Más tarde", null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }
    
    private fun sendEmailVerification() {
        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, getString(R.string.auth_verification_email_sent), Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Error al enviar verificación", Toast.LENGTH_SHORT).show()
                }
            }
    }
    
    private fun showFeatureComingSoon(featureName: String) {
        AlertDialog.Builder(this)
            .setTitle("Próximamente")
            .setMessage("La función '$featureName' será implementada en futuras versiones.")
            .setPositiveButton("OK", null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }
    
    override fun onStart() {
        super.onStart()
        // Verificar autenticación cada vez que la actividad se vuelve visible
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.d(TAG, "Usuario no autenticado en onStart, redirigiendo...")
            redirectToLogin()
        }
    }

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

}