package com.example.budgetlist

import androidx.room.*


@Dao
interface TapahtumaDao {
    @Query("SELECT * from tapahtumat")
    fun getAll(): List<Tapahtuma>

    @Insert
    fun insertAll(vararg tapahtuma: Tapahtuma)

    @Delete
    fun delete(tapahtuma: Tapahtuma)

    @Update
    fun update(vararg tapahtuma: Tapahtuma)
}