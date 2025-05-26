package com.example.s05_clinicarobles


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.s05_clinicarobles.adapter.SpecialtyAdapter
import com.example.s05_clinicarobles.model.Specialty
import android.widget.Button
class SpecialtiesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var specialties: List<Specialty>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specialties)

        specialties = listOf(
            Specialty("Traumatología", "Dr. Alberto Garcia", "Lun-Vie: 8am-1pm", "Tratamiento de lesiones musculoesqueléticas.", "999888777", "alberto@clinicarobles.pe", R.drawable.ic_trauma),
            Specialty("Cardiología", "Dr. Roberto Chavesta", "Lun-Vie: 9am-2pm", "Atención especializada en enfermedades del corazón.", "999111222", "roberto@clinicarobles.pe", R.drawable.ic_cardio),
            Specialty("Cirugía Cardiovascular", "Dr. Romen Zamudio", "Mar-Jue: 10am-1pm", "Procedimientos quirúrgicos cardiovasculares.", "988776655", "romen@clinicarobles.pe", R.drawable.ic_cardiovascular),
            Specialty("Dermatología", "Dr. Jaime Vega", "Lun-Vie: 1pm-6pm", "Diagnóstico y tratamiento de enfermedades de la piel.", "977665544", "jaime@clinicarobles.pe", R.drawable.ic_derma),
            Specialty("Ginecología", "Dra. Kelly Casanova", "Lun-Mie-Vie: 9am-12pm", "Salud reproductiva y atención integral a la mujer.", "966554433", "kelly@clinicarobles.pe", R.drawable.ic_gine),
            Specialty("Neurología", "Dr. Rosnel Valdivieso", "Lun-Jue: 8am-3pm", "Trastornos neurológicos del sistema nervioso.", "955443322", "rosnel@clinicarobles.pe", R.drawable.ic_neuro),
            Specialty("Odontología", "Dr. Pedro Cipriano", "Lun-Sab: 10am-4pm", "Salud bucal y tratamientos dentales integrales.", "944332211", "pedro@clinicarobles.pe", R.drawable.ic_dental),
            Specialty("Neumología", "Dra. Yessica Montoya", "Mar-Vie: 8am-12pm", "Atención en enfermedades respiratorias.", "933221100", "yessica@clinicarobles.pe", R.drawable.ic_pulmon)
        )

        recyclerView = findViewById(R.id.recyclerSpecialties)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SpecialtyAdapter(specialties) { specialty ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("specialty", specialty)
            startActivity(intent)

        }
        val btnBack = findViewById<Button>(R.id.btnVolver)
        btnBack.setOnClickListener {
            finish() // Finaliza la actividad actual y vuelve a la anterior
        }



    }
}

