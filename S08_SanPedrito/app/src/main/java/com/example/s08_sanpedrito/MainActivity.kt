package com.example.s08_sanpedrito

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.s08_sanpedrito.databinding.ActivityMainBinding
import com.google.android.material.chip.Chip

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar Toolbar con menú
        setSupportActionBar(binding.topAppBar)

        // Menú contextual al mantener presionado nombre
        registerForContextMenu(binding.etNombre)

        // Mostrar tarjeta de confirmación si Switch está activado
        binding.switchAsistencia.setOnCheckedChangeListener { _, isChecked ->
            binding.cardConfirmacion.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        // Evento botón registrar
        binding.btnRegistrar.setOnClickListener {
            Toast.makeText(this, "Asistencia registrada correctamente 🎉", Toast.LENGTH_SHORT).show()
        }

        // Evento popup menu sobre los chips
        binding.chipAlumno.setOnLongClickListener {
            showPopupMenu(it)
            true
        }
        binding.chipDocente.setOnLongClickListener {
            showPopupMenu(it)
            true
        }
    }

    // Menú superior (Toolbar)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Acciones del menú superior
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_terminos -> {
                startActivity(Intent(this, TermsActivity::class.java))
                true
            }
            R.id.action_acerca -> {
                Toast.makeText(this, "App desarrollada por Ingeniería de Sistemas - UNS", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Menú contextual
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_contextual, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.opcion_limpiar -> {
                binding.etNombre.text?.clear()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    // Popup menu
    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.menu_popup, popup.menu)
        popup.setOnMenuItemClickListener {
            Toast.makeText(this, "Opción: ${it.title}", Toast.LENGTH_SHORT).show()
            true
        }
        popup.show()
    }
}
