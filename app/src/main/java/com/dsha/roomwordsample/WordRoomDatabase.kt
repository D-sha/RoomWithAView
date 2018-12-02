package com.dsha.roomwordsample

import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.arch.persistence.room.Room

abstract class WordRoomDatabase : RoomDatabase () {
    abstract val wordDao: WordDao

    @Volatile
    private var INSTANCE: WordRoomDatabase? = null

    fun getDatabase(context: Context): WordRoomDatabase? {
        if (INSTANCE == null) {
            synchronized(WordRoomDatabase::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        WordRoomDatabase::class.java, "word_database"
                    ).build()
                }
            }
        }
        return INSTANCE
    }
}
