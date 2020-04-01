package com.example.koinmvvm.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.koinmvvm.data.local.database.dao.MovieDao
import com.example.koinmvvm.data.local.database.entities.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class DatabaseFile : RoomDatabase() {
    abstract val movieDao: MovieDao
}