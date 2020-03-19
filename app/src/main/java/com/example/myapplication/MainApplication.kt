package com.example.myapplication

import android.app.Application
import androidx.room.Room
//import com.example.myapplication.room.OnboardingRoomDatabase

class MainApplication : Application(){

//    private lateinit var _database: OnboardingRoomDatabase
//    val database
//        get() = _database
//
//    override fun onCreate() {
//        super.onCreate()
//
//        _database = Room.databaseBuilder(this, OnboardingRoomDatabase::class.java, "onboarding_database")
//            .build()
//    }

    companion object {
        val instance by lazy { MainApplication() }
    }
}