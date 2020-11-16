package hu.nagyhazi.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true)
    val image: String,
    val name: String,
    val email: String,
    val content: String
)