package com.example.s05_clinicarobles.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s05_clinicarobles.R
import com.example.s05_clinicarobles.model.Specialty

class SpecialtyAdapter(
    private val items: List<Specialty>,
    private val onClick: (Specialty) -> Unit
) : RecyclerView.Adapter<SpecialtyAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtDoctor: TextView = view.findViewById(R.id.txtDoctor)
        val txtSchedule: TextView = view.findViewById(R.id.txtSchedule)
        val imgIcon: ImageView = view.findViewById(R.id.imgIcon)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onClick(items[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_specialty, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.txtName.text = item.name
        holder.txtDoctor.text = item.doctor
        holder.txtSchedule.text = item.schedule
        holder.imgIcon.setImageResource(item.imageResId)
    }
}
