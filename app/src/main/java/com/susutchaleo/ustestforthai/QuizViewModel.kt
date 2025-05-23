package com.susutchaleo.ustestforthai

import android.app.Application
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.AndroidViewModel
import com.susutchaleo.ustestforthai.model.Question
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONArray
import java.io.InputStream

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions

    var currentIndex = MutableStateFlow(0)
    var showAnswer = MutableStateFlow(false)
    var selectedAnswers = mutableStateMapOf<Int, String>()
    var score = MutableStateFlow(0)

    init {
        loadQuestions(application)
    }

    private fun loadQuestions(app: Application) {
        val inputStream: InputStream = app.assets.open("questions.json")
        val json = inputStream.bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(json)

        val list = mutableListOf<Question>()
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            val optionsArray = obj.getJSONArray("options")
            val options = mutableListOf<String>()
            for (j in 0 until optionsArray.length()) {
                options.add(optionsArray.getString(j))
            }

            list.add(
                Question(
                    id = obj.getInt("id"),
                    question = obj.getString("question"),
                    options = options,
                    answer = obj.getString("answer"),
                    thai = obj.getString("thai")
                )
            )
        }
        _questions.value = list
    }

    fun selectAnswer(questionId: Int, answer: String) {
        selectedAnswers[questionId] = answer
        updateScore()
    }

    private fun updateScore() {
        score.value = _questions.value.count {
            selectedAnswers[it.id] == it.answer
        }
    }

    fun resetQuiz() {
        currentIndex.value = 0
        showAnswer.value = false
        selectedAnswers.clear()
        score.value = 0
    }
}