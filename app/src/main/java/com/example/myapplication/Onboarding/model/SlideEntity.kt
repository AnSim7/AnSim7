package com.example.myapplication.Onboarding.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "slides_table")
class SlideEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "number")
    val number: Long,
    @ColumnInfo(name = "subtitle")
    val subtitle: String,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "targetLink")
    val targetLink: String,
    @ColumnInfo(name = "image")
    val image: String
)
