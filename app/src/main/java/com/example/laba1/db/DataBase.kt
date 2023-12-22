package com.example.laba1.db

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.laba1.db.dao.DaoInterface
import com.example.laba1.db.entities.Recipe
import com.example.laba1.db.entities.User
import com.example.laba1.db.entities.UsersRecipesJunction

@Database(entities = [Recipe::class, User::class, UsersRecipesJunction::class],version = 1)
abstract class DataBase: RoomDatabase() {
    abstract fun getDao(): DaoInterface

    companion object{
        fun getDatabase(context: Context): DataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                DataBase::class.java,
                "mydb2.db"
            ).build()
        }
    }
}