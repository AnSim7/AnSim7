package com.example.myapplication.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [OnboardingDbModel::class], version = 1, exportSchema = false)
abstract class OnboardingRoomDatabase : RoomDatabase() {

    abstract fun onboaringDao(): OnboaringDao

    companion object {
        @Volatile
        private var INSTANCE: OnboardingRoomDatabase? = null

        fun getDatabase(context: Context): OnboardingRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OnboardingRoomDatabase::class.java,
                    "onboarding_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

