package hu.nagyhazi.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    exportSchema = false,
    entities = [User::class]
)
abstract class UserDatabase: RoomDatabase(){
    abstract fun userDao(): UserDao
}