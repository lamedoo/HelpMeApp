package com.lukakordzaia.helpmeapp.network.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Users::class], version = 2)
abstract class HelpMeAppDatabase : RoomDatabase() {

    abstract fun getDao(): UsersDao

    companion object {
        private var database: HelpMeAppDatabase? = null

        fun getDatabase(context: Context): HelpMeAppDatabase? {
            database ?: kotlin.run {
                database = Room.databaseBuilder(context, HelpMeAppDatabase::class.java, "usersDB")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return database
        }
    }
}