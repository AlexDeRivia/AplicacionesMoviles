package com.example.s02_registroalumnos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etContra: EditText
    private lateinit var etNombres: EditText
    private lateinit var etApellidos: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etContraRegistro: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnInicio: Button
    private lateinit var btnRegistrarse: Button
    private lateinit var tvRegistrarse: TextView
    private lateinit var tvNuevoRegistro: TextView
    private lateinit var tvBienvenido: TextView
    private lateinit var tvTitulo: TextView
    private lateinit var llRegistro: LinearLayout
    private lateinit var llUsuario: LinearLayout
    private lateinit var llContra: LinearLayout
    //private lateinit var tablaAlumnos: TableLayout // Tabla para mostrar los registros

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Layout de login

        // Inicializar las vistas
        inicializarVistas()

        // Listener para el botón Login
        btnLogin.setOnClickListener {
            val usuario = etUsuario.text.toString()
            val contra = etContra.text.toString()

            if (usuario.isEmpty() || contra.isEmpty()) {
                // Mostrar error si hay campos vacíos
                etUsuario.error = "Requerido"
                etContra.error = "Requerido"
            } else if (usuario == "Admin" && contra == "12345") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Mostrar mensaje de error
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                // Limpiar campos
                etUsuario.setText("")
                etContra.setText("")
            }
        }

        // Botón de registrar
        tvRegistrarse.setOnClickListener {
            toggleViews(false) // Cambiar visibilidad
        }

        // Botón de registrarse (cuando se completa el registro)
        btnRegistrarse.setOnClickListener {
            val codigo = etContraRegistro.text.toString()
            val apellidos = etApellidos.text.toString()
            val nombres = etNombres.text.toString()
            val correo = etCorreo.text.toString()


            if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || codigo.isEmpty()) {
                // Mostrar errores si hay campos vacíos
                etNombres.error = "Requerido"
                etApellidos.error = "Requerido"
                etCorreo.error = "Requerido"
                etContraRegistro.error = "Requerido"
            } else if (!esCorreoValido(correo)) {
                // Validar el correo
                etCorreo.error = "Correo inválido"
            } else {
                // Guardar registro
                guardarRegistro(codigo, apellidos, nombres, correo)

                // Limpiar campos
                limpiarCampos()

                // Ocultar el formulario de registro
                toggleViews(true)
            }
        }

        // Botón Inicio (volver al login)
        btnInicio.setOnClickListener {
            // Mostrar el formulario de login
            toggleViews(true)

            // Limpiar campos
            limpiarCampos()
        }
    }

    private fun inicializarVistas() {
        // Referenciar las vistas
        etUsuario = findViewById(R.id.etUsuario)
        etContra = findViewById(R.id.etContra)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegistrarse = findViewById(R.id.tvRegistrarse)
        tvNuevoRegistro = findViewById(R.id.tvNuevoRegistro)
        tvTitulo = findViewById(R.id.tvTitulo)
        tvBienvenido = findViewById(R.id.tvBienvenido)
        btnInicio = findViewById(R.id.btnInicio)
        llRegistro = findViewById(R.id.llRegistro)
        etNombres = findViewById(R.id.etNombres)
        etApellidos = findViewById(R.id.etApellidos)
        etCorreo = findViewById(R.id.etCorreo)
        etContraRegistro = findViewById(R.id.etContraRegistro)
        btnRegistrarse = findViewById(R.id.btnRegistrarse)
        llUsuario = findViewById(R.id.llUsuario)
        llContra = findViewById(R.id.llContra)
        //tablaAlumnos = findViewById(R.id.tablaAlumnos)
    }

    private fun toggleViews(isLogin: Boolean) {
        if (isLogin) {
            llUsuario.visibility = View.VISIBLE
            llContra.visibility = View.VISIBLE
            btnLogin.visibility = View.VISIBLE
            tvRegistrarse.visibility = View.VISIBLE
            tvNuevoRegistro.visibility = View.VISIBLE
            tvTitulo.visibility = View.VISIBLE
            tvBienvenido.visibility = View.GONE
            btnInicio.visibility = View.GONE
            llRegistro.visibility = View.GONE
        } else {
            llUsuario.visibility = View.GONE
            llContra.visibility = View.GONE
            btnLogin.visibility = View.GONE
            tvRegistrarse.visibility = View.GONE
            tvNuevoRegistro.visibility = View.GONE
            tvTitulo.visibility = View.GONE
            llRegistro.visibility = View.VISIBLE
            btnInicio.visibility = View.VISIBLE
        }
    }

    private fun esCorreoValido(correo: String): Boolean {
        val emailRegex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
        return correo.matches(emailRegex.toRegex())
    }

    private fun limpiarCampos() {
        etNombres.setText("")
        etApellidos.setText("")
        etCorreo.setText("")
        etContraRegistro.setText("")
        etUsuario.setText("")
        etContra.setText("")
    }

    private fun guardarRegistro(codigo: String, apellidos: String, nombres: String, correo: String) {
        val prefs = getSharedPreferences("registroPrefs", MODE_PRIVATE)
        val editor = prefs.edit()
        val registrosStr = prefs.getString("registros", "[]")
        val registrosJson = org.json.JSONArray(registrosStr)

        val nuevoRegistro = org.json.JSONObject()
        nuevoRegistro.put("codigo", codigo)
        nuevoRegistro.put("apellidos", apellidos)
        nuevoRegistro.put("nombres", nombres)
        nuevoRegistro.put("correo", correo)


        registrosJson.put(nuevoRegistro)
        editor.putString("registros", registrosJson.toString())
        editor.apply()
    }


}
