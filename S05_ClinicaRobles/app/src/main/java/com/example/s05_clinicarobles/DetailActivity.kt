package com.example.s05_clinicarobles

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.s05_clinicarobles.model.Specialty

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val specialty = intent.getSerializableExtra("specialty") as? Specialty

        if (specialty != null) {
            findViewById<TextView>(R.id.txtName).text = specialty.name
            findViewById<TextView>(R.id.txtDoctor).text = specialty.doctor
            findViewById<TextView>(R.id.txtDescription).text = specialty.description
            findViewById<TextView>(R.id.txtPhone).text = "Tel: ${specialty.phone}"
            findViewById<TextView>(R.id.txtEmail).text = "Correo: ${specialty.email}"

            val iconImageView = findViewById<ImageView>(R.id.imgIcon)
            iconImageView.setImageResource(specialty.imageResId)

            val btnVolver = findViewById<Button>(R.id.btnVolver)
            btnVolver.setOnClickListener {
                finish()
            }
        }
    }
}
