package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * Fragmento donde se juega el juego
 */
class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        // Infle la vista y obtenga una instancia de la clase vinculante
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        Log.i("GameFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // Establezca el modelo de vista para el enlace de datos: esto permite el acceso al diseño enlazado
        // a todos los datos en el ViewModel
        binding.gameViewModel = viewModel

        // Especifique la vista de fragmentos como propietario del ciclo de vida del enlace.
        // Esto se usa para que el enlace pueda observar las actualizaciones de LiveData.
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer<Boolean> { hasFinished ->
            if (hasFinished) gameFinished()
        })


        /*
        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }
        binding.endGameButton.setOnClickListener { onEndGame() } */

        /*
        /** Setting up LiveData observation relationship // Una expresión lambda **/
        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })  */

        /*
        /** Setting up LiveData observation relationship **/
        viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
            binding.wordText.text = newWord
        })  */

        return binding.root

    }


    /** Métodos para presionar botones**/
    /*
    private fun onSkip() {
        viewModel.onSkip()

    }

    private fun onCorrect() {
        viewModel.onCorrect()

    }

    //salir del juego
    private fun onEndGame() {
        gameFinished()
    }  */

    /**
     * Calledo cuando el juego a terminado
     */
    private fun gameFinished() {
        Toast.makeText(activity, "El juego acaba de terminar", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections.actionGameToScore()
        action.score = viewModel.score.value?:0
        NavHostFragment.findNavController(this).navigate(action)
        viewModel.onGameFinishComplete()
    }


    /** Métodos para actualizar la interfaz de usuario **/
    /* //dejara de usarse ya que ahora se actaliza con liveData
    private fun updateWordText() {
        binding.wordText.text = viewModel.word.value
    }

    private fun updateScoreText() {
        binding.scoreText.text = viewModel.score.value.toString()
    }*/
}
