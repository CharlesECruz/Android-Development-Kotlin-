package com.example.colorquiz

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var textScore : TextView
    private lateinit var textQuestion : TextView
    private lateinit var colourLeft : Button
    private lateinit var colourRight : Button
    private val colourArray = Colour.values()
    private val colourArraySize = colourArray.size
    private var score = 0
    private var correctAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContentView(R.layout.activity_main)

        textScore = findViewById(R.id.textView_score)
        textQuestion = findViewById(R.id.textView_question)
        colourLeft = findViewById(R.id.button_left)
        colourRight = findViewById(R.id.button_right)
        initializate()

    }

    private fun initializate(){
        setScore(score)
        generateRandomColor()
    }
    private fun generateRandomColor() {
        val random = Random(System.currentTimeMillis())
        val ranNum1 = random.nextInt(colourArraySize)
        var ranNum2: Int
        do {
            ranNum2 = random.nextInt(colourArraySize)
        } while (ranNum1 == ranNum2)

        colourLeft.setBackgroundColor(getColorCode(colourArray[ranNum1]))
        colourRight.setBackgroundColor(getColorCode(colourArray[ranNum2]))

        correctAnswer = random.nextInt(2)
        textQuestion.text =
            colourArray[if (correctAnswer == 0) ranNum1 else ranNum2].name
                .replace("_", " ").capitalize()
    }

    private fun setScore(score: Int) {
        textScore.text = String.format("Score : %d",score)
    }

    fun onColor(view:View){
        val currentAnswer = when (view.id){
            R.id.button_right -> 1
            R.id.button_left -> 0
            else -> -1
        }
        if (currentAnswer == correctAnswer) {
            Toast.makeText(this, "Right answer :)", Toast.LENGTH_SHORT).show()
            setScore(++score)
        } else {
        Toast.makeText(this, "Wrong answer :(", Toast.LENGTH_SHORT).show()
        }
        generateRandomColor()
    }


    fun getColorCode(c: Colour) = when (c) {
        Colour.WHITE -> Color.WHITE
        Colour.LIGHT_GRAY -> Color.LTGRAY
        Colour.GRAY -> Color.GRAY
        Colour.DARK_GRAY -> Color.DKGRAY
        Colour.BLACK -> Color.BLACK
        Colour.RED -> Color.RED
        Colour.YELLOW -> Color.YELLOW
        Colour.GREEN -> Color.GREEN
        Colour.MAGENTA -> Color.MAGENTA
        Colour.CYAN -> Color.CYAN
        Colour.BLUE -> Color.BLUE
    }

    enum class Colour {
        WHITE, LIGHT_GRAY, GRAY, DARK_GRAY, BLACK, RED, YELLOW, GREEN, MAGENTA, CYAN, BLUE
    }
}