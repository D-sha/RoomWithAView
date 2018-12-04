package com.dsha.roomwordsample

import android.arch.lifecycle.LiveData
import android.app.Application
import android.arch.lifecycle.AndroidViewModel

class WordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WordRepository = WordRepository(application)

    val allWords: LiveData<List<Word>>? = repository.rgetAllWords()

    fun insert(word: Word) {
        repository.insert(word)
    }
}