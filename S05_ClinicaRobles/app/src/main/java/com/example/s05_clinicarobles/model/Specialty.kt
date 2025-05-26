package com.example.s05_clinicarobles.model

import java.io.Serializable

data class Specialty(
    val name: String,
    val doctor: String,
    val schedule: String,
    val description: String,
    val phone: String,
    val email: String,
    val imageResId: Int
) : Serializable
