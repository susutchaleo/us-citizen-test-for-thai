package com.susutchaleo.ustestforthai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.susutchaleo.ustestforthai.ui.HomeScreen
import com.susutchaleo.ustestforthai.ui.QuizScreen
import com.susutchaleo.ustestforthai.ui.ResultScreen
import com.susutchaleo.ustestforthai.ui.theme.USCitizenTestForThaiTheme

class MainActivity : ComponentActivity() {

    private val viewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var screenState by mutableStateOf("home")

        setContent {
            when (screenState) {
                "home" -> HomeScreen {
                    screenState = "quiz"
                }

                "quiz" -> QuizScreen(viewModel) {
                    screenState = "result"
                }

                "result" -> ResultScreen(viewModel) {
                    screenState = "home"
                }
            }
        }
    }
}


