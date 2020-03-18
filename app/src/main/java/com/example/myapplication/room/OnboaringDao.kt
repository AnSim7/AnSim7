package com.example.myapplication.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OnboaringDao {
    @Query("SELECT * FROM OnboardingModel")
    fun getAll(): LiveData<List<OnboardingDbModel?>?>

    @JvmSuppressWildcards
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(slides: OnboardingDbModel)
}