package com.example.myapplication.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.onboarding_project.TypeOnboarding
import com.example.onboarding_project.Slide

@Entity(tableName = OnboardingDbModel.TABLE_NAME)
data class OnboardingDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "type")
    @NonNull
    val type: String,
    @ColumnInfo(name = "number")
    val number: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "subtitle")
    val subtitle: String,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "targetLink")
    val targetLink: String,
    @ColumnInfo(name = "image")
    val image: String
) {
    companion object {
        const val TABLE_NAME = "OnboardingModel"

        fun fromOnboarding(type: String, num: Int, slide: Slide) = OnboardingDbModel(
            type = type,
            number = num,
            title = slide.title,
            subtitle = slide.subtitle,
            text = slide.text,
            targetLink = slide.targetLink,
            image = slide.image
        )
    }
}