package com.dsha.roomwordsample

import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.arch.persistence.room.Room
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.os.AsyncTask
@Database(entities = [Word::class], version = 1, exportSchema = false)
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

        private fun buildDatabase(context: Context): WordRoomDatabase {
            return Room.databaseBuilder(
                            context.applicationContext,
                            WordRoomDatabase::class.java, "word_database"
                        ).addCallback(sRoomDatabaseCallback).build()
        }

        fun getDatabase(context: Context): WordRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }
    }
}