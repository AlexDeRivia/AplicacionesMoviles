package com.example.s02_registroalumnos
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class ExitFragment : Fragment(R.layout.fragment_exit) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mostrar el cuadro de confirmación al llegar a este fragmento
        showExitConfirmationDialog()
    }

    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("¿Desea salir?")
            .setCancelable(false) // No permite cerrar tocando fuera del cuadro
            .setPositiveButton("Sí") { dialog, id ->
                // Si elige "Sí", regresa al LoginActivity
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish() // Finaliza la actividad actual
            }
            .setNegativeButton("No") { dialog, id ->
                // Si elige "No", regresa al HomeFragment
                activity?.onBackPressed() // Regresa al fragmento anterior (HomeFragment)
            }

        val alert = builder.create()
        alert.show()
    }
}
