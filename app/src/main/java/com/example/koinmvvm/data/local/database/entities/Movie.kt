package com.example.koinmvvm.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
data class Movie(
    @PrimaryKey
    val id: Int? = null,
    val name: String?
)