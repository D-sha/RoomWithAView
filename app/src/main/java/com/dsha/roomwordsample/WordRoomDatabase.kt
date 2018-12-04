package com.dsha.roomwordsample

import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.arch.persistence.room.Room
import android.os.AsyncTask.execute
import android.arch.persistence.db.SupportSQLiteDatabase
import android.support.annotation.NonNull
import android.os.AsyncTask





abstract class WordRoomDatabase : RoomDatabase () {
    abstract val wordDao: WordDao


    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { PopulateDbAsync(it).execute() }
            }
        }

        private class PopulateDbAsync internal constructor(db: WordRoomDatabase) : AsyncTask<Void, Void, Void>() {

            private val mDao: WordDao = db.wordDao

            override fun doInBackground(vararg params: Void): Void? {
                mDao.deleteAll()
                var word = Word("Hello")
                mDao.insert(word)
                word = Word("World")
                mDao.insert(word)
                return null
            }
        }
        fun getDatabase(context: Context): WordRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(WordRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            WordRoomDatabase::class.java, "word_database"
                        ).addCallback(sRoomDatabaseCallback).build()
                    }
                }
            }
            return INSTANCE
        }
    }


}