package com.example.myapplication.Onboarding.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(SlideEntity::class), version = 1, exportSchema = false)
abstract class SlideRoomDatabase : RoomDatabase() {

    abstract fun slideDao(): SlideDao

    companion object {
        @Volatile
        private var INSTANCE: SlideRoomDatabase? = null

        fun getDatabase(
            context: Context
        ): SlideRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SlideRoomDatabase::class.java,
                    "onboarding_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}