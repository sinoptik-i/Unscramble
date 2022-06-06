package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//class GameViewModel{
class GameViewModel : ViewModel() {

    private var _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private var wordsList: MutableList<String> = mutableListOf()

    private var _currentWordCount = MutableLiveData(wordsList.count())
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    private var _currentScrambledWord = MutableLiveData<String>()
    val currentScrambledWord
        get() = _currentScrambledWord


    lateinit var currentWord: String

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
        // currentWord=wordsList.last()
    }


    fun isUserWordCorrect(playerWord: String): Boolean {
        return if (playerWord.equals(currentWord)) {
            increaseScore()
            true
        } else false
    }

    private fun increaseScore() {
        _score.value = _score.value?.plus(SCORE_INCREASE)
    }

    fun getNextWord() {
        currentWord = allWordsList.random()
        while (wordsList.contains(currentWord)) {
            currentWord = allWordsList.random()
        }
        _currentScrambledWord.value = currentWord
        while (_currentScrambledWord.value.equals(currentWord, false)) {
            val tempWord = _currentScrambledWord.value!!.toCharArray()
            tempWord.shuffle()
            _currentScrambledWord.value = String(tempWord)
        }
        wordsList.add(currentWord)
        _currentWordCount.value=wordsList.count()
    }

   /* private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value=(_currentWordCount.value)?.inc()
            wordsList.add(currentWord)
        }
    }*/

    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    fun reinitializeData() {
        _score.value = 0;
        wordsList.clear()
        getNextWord()
    }


}