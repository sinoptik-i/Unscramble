package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

//class GameViewModel{
class GameViewModel : ViewModel() {

    private var _score = 0
    val score
        get() = _score
    private var wordsList: MutableList<String> = mutableListOf()


    private val currentWordCount
        get() = wordsList.count()

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord
        get() = _currentScrambledWord


     lateinit var currentWord: String
    /*get() = if (wordsList.isEmpty()){
        ""
    }
else
    wordsList.last()*/
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
        _score += SCORE_INCREASE
    }

    fun getNextWord() {
        currentWord = allWordsList.random()
        while (wordsList.contains(currentWord)) {
            currentWord = allWordsList.random()
        }
        _currentScrambledWord = currentWord
        while (_currentScrambledWord.equals(currentWord, false)) {
            val tempWord = _currentScrambledWord.toCharArray()
            tempWord.shuffle()
            _currentScrambledWord = String(tempWord)
            // currentWord.toCharArray().shuffle().toString()// String(shuffledCurrentWord)
        }
        wordsList.add(currentWord)
    }

    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    fun reinitializeData(){
        _score=0;
        wordsList.clear()
        getNextWord()
    }


}