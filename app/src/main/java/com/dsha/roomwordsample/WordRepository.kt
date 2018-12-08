package com.dsha.roomwordsample

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

class WordRepository(application: Application) {
    private val wordRoomDataBase = WordRoomDatabase.getDatabase(application)
    private val wordDao: WordDao? = wordRoomDataBase.wordDao
    private val allWords = wordDao?.getAllWords()

    fun rgetAllWords(): LiveData<List<Word>>? {
        return allWords
    }

    fun insert(word: Word) {
        insertAsyncTask(wordDao).execute(word);
    }

    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: WordDao?) :
        AsyncTask<Word, Void, Void>() {

        override fun doInBackground(vararg params: Word): Void? {
            mAsyncTaskDao?.insert(params[0])
            return null
        }
    }
}