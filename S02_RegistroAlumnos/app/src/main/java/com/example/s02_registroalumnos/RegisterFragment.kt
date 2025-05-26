package com.example.s02_registroalumnos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import org.json.JSONArray
import org.json.JSONObject

class RegisterFragment : Fragment() {

    private lateinit var tableLayout: TableLayout
    private lateinit var checkCodigo: CheckBox
    private lateinit var checkApellidos: CheckBox
    private lateinit var contadorAlumnos: TextView
    private val registros = mutableListOf<JSONObject>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tableLayout = view.findViewById(R.id.tablaAlumnos)
        checkCodigo = view.findViewById(R.id.ordenarPorCodigo)
        checkApellidos = view.findViewById(R.id.ordenarPorApellidos)
        contadorAlumnos = view.findViewById(R.id.cantidadAlumnos)

        val prefs = requireActivity().getSharedPreferences("registroPrefs", AppCompatActivity.MODE_PRIVATE)
        val registrosStr = prefs.getString("registros", "[]")
        val registrosJson = JSONArray(registrosStr)

        // Convertimos a una lista de objetos
        for (i in 0 until registrosJson.length()) {
            registros.add(registrosJson.getJSONObject(i))
        }

        // Función para ordenar y mostrar
        fun ordenarYMostrar() {
            when {
                checkCodigo.isChecked -> registros.sortBy { it.getString("codigo") }
                checkApellidos.isChecked -> registros.sortBy { it.getString("apellidos").lowercase() }
            }
            mostrarRegistros()
        }

        // Eventos de cambio en los CheckBoxes
        checkCodigo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkApellidos.isChecked = false
                ordenarYMostrar()
            } else if (!checkApellidos.isChecked) {
                mostrarRegistros()
            }
        }

        checkApellidos.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkCodigo.isChecked = false
                ordenarYMostrar()
            } else if (!checkCodigo.isChecked) {
                mostrarRegistros()
            }
        }

        // Mostrar registros inicialmente
        mostrarRegistros()
    }

    private fun mostrarRegistros() {
        // Eliminar todas las filas excepto la cabecera (índice 0)
        tableLayout.removeViews(1, tableLayout.childCount - 1)
        for (reg in registros) {
            val codigo = reg.getString("codigo")
            val apellidos = reg.getString("apellidos")
            val nombres = reg.getString("nombres")
            val correo = reg.getString("correo")
            addUserToTable(codigo, apellidos, nombres, correo)
        }
        // Actualiza el contador de estudiantes registrados
        contadorAlumnos.text = registros.size.toString()
    }

    private fun addUserToTable(codigo: String, apellidos: String, nombres: String, correo: String) {
        val newRow = TableRow(requireContext())
        listOf(codigo, apellidos, nombres, correo).forEach { texto ->
            val cell = TextView(requireContext())
            cell.text = texto
            cell.setPadding(16, 8, 16, 8)
            cell.setBackgroundResource(R.drawable.celda_dato)
            newRow.addView(cell)
        }
        tableLayout.addView(newRow)
    }
}
