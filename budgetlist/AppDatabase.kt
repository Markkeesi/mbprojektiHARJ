package com.example.budgetlist

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = arrayOf(Tapahtuma::class), version = 1)
abstract  class AppDatabase : RoomDatabase() {
    abstract  fun tapahtumaDao() : TapahtumaDao
}

