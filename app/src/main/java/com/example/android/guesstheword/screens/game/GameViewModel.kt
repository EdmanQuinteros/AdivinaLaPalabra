package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private val timer: CountDownTimer

    companion object {
        // Hora en que termina el juego
        private const val DONE = 0L

        // Intervalo de tiempo de cuenta regresiva
        private const val ONE_SECOND = 1000L

        // Tiempo total del juego
        private const val COUNTDOWN_TIME = 60000L
    }

    // tiempo de cuenta regresiva
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime


    // La versión String de la hora actual
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }


    // la palabra actual /uso de liveData/
    private val _word = MutableLiveData<String>()       //Los datos en un MutableLiveDataobjeto se pueden cambiar
    val word: LiveData<String>                          //Los datos de un LiveDataobjeto se pueden leer, pero no cambiar
        get() = _word

    // La puntuación actual
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    // Evento que desencadena el final del juego
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
    get() = _eventGameFinish

    // La pista para la palabra actual
    val wordHint = Transformations.map(word) { word ->
        val randomPosition = (1..word.length).random()
        "La palabra actual tiene " + word.length + " letras" +
                "\nLa letra en la posición " + randomPosition + " es " +
                word.get(randomPosition - 1).toUpperCase()
    }


    //La lista de palabras - el frente de la lista es la siguiente palabra a adivinar
    private lateinit var wordList: MutableList<String>

    init {
        _word.value = ""
        _score.value = 0
        resetList()
        nextWord()

        // Crea un temporizador que activa el final del juego cuando termina
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished/ONE_SECOND
            }

            override fun onFinish() {
                _currentTime.value = DONE
                onGameFinish()
            }
        }

        timer.start()
        Log.i("GameViewModel", "GameViewModel created!")
    }



    override fun onCleared() {
        super.onCleared()

        // Cancel the timer
        timer.cancel()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }

    /**
     * Restablece la lista de palabras y aleatoriza el orden
     */
    private fun resetList() {
        wordList = mutableListOf(
            "bebé",
            "limbo",
            "reina",
            "cantar",
            "payaso",
            "avión",
            "ladrón",
            "futbol",
            "hospital",
            "banano",
            "basketball",
            "profesor",
            "estirar",
            "rapear",
            "hamaca",
            "gato",
            "pizza",
            "robot",
            "tambor",
            "calambre",
            "boxeo",
            "caracol",
            "barberia",
            "sopa",
            "calculadora",
            "calendario",
            "rana",
            "león",
            "elefante",
            "triste",
            "pato",
            "escritorio",
            "guitarra",
            "abuela",
            "flores",
            "casa",
            "ferrocarril",
            "maquillaje",
            "canguro",
            "patinar",
            "gelatina",
            "carro",
            "martillo",
            "sombrero",
            "Cuervo",
            "bolsa",
            "rollo",
            "burbuja"
        )
        wordList.shuffle()
    }

    /**
     * Se mueve a la siguiente palabra en la lista
     */
    private fun nextWord() {
        if (wordList.isEmpty()) {
            resetList()
            //onGameFinish()   //ya no hay palabras
        } else {
            //Select and remove a word from the list
            _word.value = wordList.removeAt(0)
        }
    }

    /** Métodos para presionar botones **/

    fun onSkip() {
        //minus() función, realiza la resta con null-safety.
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    fun onGameFinish(){
        _eventGameFinish.value = true
    }

    /** Método para el evento de juego completado **/
    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }


}