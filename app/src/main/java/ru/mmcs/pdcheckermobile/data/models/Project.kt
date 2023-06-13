package ru.mmcs.pdcheckermobile.data.models

data class Project(
    val publicId: String,
    val name: String,
    val description: String,
    val grades: List<Grade>,
    val buildUrl: String
)
