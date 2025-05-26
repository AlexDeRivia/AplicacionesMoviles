package com.example.s02_registroalumnos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val paginaUns: ImageView = view.findViewById(R.id.pagina_uns)
        val repositorioUns: ImageView = view.findViewById(R.id.repositorio_uns)
        val campusVirtual: ImageView = view.findViewById(R.id.campus_virtual_uns)
        val siigaa: ImageView = view.findViewById(R.id.siigaa)

        paginaUns.setOnClickListener { abrirUrl("https://www.uns.edu.pe/#/principal") }
        repositorioUns.setOnClickListener { abrirUrl("https://repositorio.uns.edu.pe/") }
        campusVirtual.setOnClickListener { abrirUrl("https://www.uns.edu.pe/campusvirtual/login/index.php") }
        siigaa.setOnClickListener { abrirUrl("https://registro.uns.edu.pe/udemsi/") }
    }

    private fun abrirUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
