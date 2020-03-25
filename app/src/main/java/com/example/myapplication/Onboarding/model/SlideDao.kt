package com.example.myapplication.Onboarding.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SlideDao {

    @Query("SELECT * from slides_table where type = :needType order by number")
    fun getSlides(needType: String): LiveData<List<SlideEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(slides: List<SlideEntity>)

    @Query("DELETE FROM slides_table WHERE type = :needType")
    fun deleteAll(needType: String)

}

