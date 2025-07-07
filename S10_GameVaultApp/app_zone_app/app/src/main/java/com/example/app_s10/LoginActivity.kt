package com.example.app_s10

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.widget.ProgressBar
import android.widget.TextView

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    
    // Views del layout
    private lateinit var inputLayoutEmail: TextInputLayout
    private lateinit var inputLayoutPassword: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var btnRegister: MaterialButton
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvGuestLogin: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvStatus: TextView
    
    // Variables de estado
    private var isLoginMode = true
    
    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()
        
        // Inicializar views
        initializeViews()
        
        // Configurar listeners
        setupClickListeners()
        
        // Verificar si ya hay un usuario autenticado
        checkCurrentUser()
    }
    
    private fun initializeViews() {
        inputLayoutEmail = findViewById(R.id.input_layout_email)
        inputLayoutPassword = findViewById(R.id.input_layout_password)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        btnRegister = findViewById(R.id.btn_register)
        tvForgotPassword = findViewById(R.id.tv_forgot_password)
        tvGuestLogin = findViewById(R.id.tv_guest_login)
        progressBar = findViewById(R.id.progress_bar)
        tvStatus = findViewById(R.id.tv_status)
    }
    
    private fun setupClickListeners() {
        btnLogin.setOnClickListener {
            if (isLoginMode) {
                performLogin()
            } else {
                performRegister()
            }
        }
        
        btnRegister.setOnClickListener {
            toggleMode()
        }
        
        tvForgotPassword.setOnClickListener {
            showForgotPasswordDialog()
        }
        
        tvGuestLogin.setOnClickListener {
            proceedAsGuest()
        }
    }
    
    private fun checkCurrentUser() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.d(TAG, "Usuario ya autenticado: ${currentUser.email}")
            navigateToMainActivity(currentUser)
        }
    }
    
    private fun performLogin() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        
        // Validar campos
        if (!validateInput(email, password)) {
            return
        }
        
        showLoading(true)
        updateStatus(getString(R.string.loading))
        
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                showLoading(false)
                
                if (task.isSuccessful) {
                    Log.d(TAG, "Login exitoso")
                    val user = auth.currentUser
                    showSuccessMessage(getString(R.string.login_success))
                    navigateToMainActivity(user)
                } else {
                    Log.w(TAG, "Login falló", task.exception)
                    handleAuthError(task.exception)
                }
            }
    }
    
    private fun performRegister() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        
        // Validar campos
        if (!validateInput(email, password)) {
            return
        }
        
        // Validación adicional para registro
        if (password.length < 6) {
            inputLayoutPassword.error = getString(R.string.error_weak_password)
            return
        }
        
        showLoading(true)
        updateStatus(getString(R.string.loading))
        
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                showLoading(false)
                
                if (task.isSuccessful) {
                    Log.d(TAG, "Registro exitoso")
                    val user = auth.currentUser
                    showSuccessMessage(getString(R.string.register_success))
                    
                    // Enviar email de verificación
                    sendEmailVerification(user)
                    
                    navigateToMainActivity(user)
                } else {
                    Log.w(TAG, "Registro falló", task.exception)
                    handleAuthError(task.exception)
                }
            }
    }
    
    private fun validateInput(email: String, password: String): Boolean {
        var isValid = true
        
        // Limpiar errores previos
        inputLayoutEmail.error = null
        inputLayoutPassword.error = null
        
        // Validar email
        if (email.isEmpty()) {
            inputLayoutEmail.error = getString(R.string.error_empty_email)
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputLayoutEmail.error = getString(R.string.error_invalid_email)
            isValid = false
        }
        
        // Validar contraseña
        if (password.isEmpty()) {
            inputLayoutPassword.error = getString(R.string.error_empty_password)
            isValid = false
        }
        
        return isValid
    }
    
    private fun handleAuthError(exception: Exception?) {
        val errorMessage = when (exception?.message) {
            "There is no user record corresponding to this identifier. The user may have been deleted." -> 
                getString(R.string.error_user_not_found)
            "The password is invalid or the user does not have a password." -> 
                getString(R.string.error_wrong_password)
            "The email address is already in use by another account." -> 
                getString(R.string.error_email_already_in_use)
            "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> 
                getString(R.string.error_network)
            else -> if (isLoginMode) getString(R.string.error_login_failed) else getString(R.string.error_register_failed)
        }
        
        showErrorMessage(errorMessage)
        updateStatus("")
    }
    
    private fun showForgotPasswordDialog() {
        val email = etEmail.text.toString().trim()
        
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showErrorMessage(getString(R.string.error_invalid_email))
            return
        }
        
        showLoading(true)
        updateStatus("Enviando correo de recuperación...")
        
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                showLoading(false)
                
                if (task.isSuccessful) {
                    showSuccessMessage(getString(R.string.auth_password_reset_sent))
                    updateStatus("")
                } else {
                    showErrorMessage("Error al enviar correo de recuperación")
                    updateStatus("")
                }
            }
    }
    
    private fun sendEmailVerification(user: FirebaseUser?) {
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email de verificación enviado")
                    showSuccessMessage(getString(R.string.auth_verification_email_sent))
                }
            }
    }
    
    private fun proceedAsGuest() {
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Login anónimo exitoso")
                    val user = auth.currentUser
                    showSuccessMessage("¡Bienvenido, invitado!")
                    navigateToMainActivity(user)
                } else {
                    Log.w(TAG, "Login anónimo falló", task.exception)
                    showErrorMessage("Error al continuar como invitado")
                }
            }
    }
    
    private fun toggleMode() {
        isLoginMode = !isLoginMode
        
        if (isLoginMode) {
            // Cambiar a modo login
            btnLogin.text = getString(R.string.btn_login)
            btnRegister.text = getString(R.string.btn_register)
            tvForgotPassword.visibility = View.VISIBLE
        } else {
            // Cambiar a modo registro
            btnLogin.text = getString(R.string.btn_register)
            btnRegister.text = "Volver al login"
            tvForgotPassword.visibility = View.GONE
        }
        
        // Limpiar campos y errores
        etEmail.text?.clear()
        etPassword.text?.clear()
        inputLayoutEmail.error = null
        inputLayoutPassword.error = null
    }
    
    private fun navigateToMainActivity(user: FirebaseUser?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        btnLogin.isEnabled = !show
        btnRegister.isEnabled = !show
    }
    
    private fun updateStatus(message: String) {
        tvStatus.text = message
        tvStatus.visibility = if (message.isEmpty()) View.GONE else View.VISIBLE
    }
    
    private fun showSuccessMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
    
    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}