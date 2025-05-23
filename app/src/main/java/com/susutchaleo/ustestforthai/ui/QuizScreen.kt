package com.susutchaleo.ustestforthai.ui

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.susutchaleo.ustestforthai.QuizViewModel
import com.susutchaleo.ustestforthai.R

@Composable
fun QuizScreen(viewModel: QuizViewModel, onFinish: () -> Unit) {
    val questions by viewModel.questions.collectAsState()
    val index by viewModel.currentIndex.collectAsState()
    val showAnswer by viewModel.showAnswer.collectAsState()

    // Text-to-Speech
    val context = LocalContext.current
    val tts = remember {
        TextToSpeech(context) { status ->
                if (status != TextToSpeech.ERROR) {
                    //tts.language = Locale.US
                }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            tts.stop()
            tts.shutdown()
        }
    }

    // Question
    if (questions.isEmpty()) return

    val question = questions[index]
    val selected = viewModel.selectedAnswers[question.id]
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
        .fillMaxSize()
            .verticalScroll(scrollState)
        .padding(14.dp)) {

        //banner
        Image(
            painter = painterResource(id = R.drawable.banner),
            contentDescription = "Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        //Question
        Text(
            text = "Question ${index + 1}/ ${questions.size}",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFE4E1))
                .padding(8.dp)
            )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = question.question,
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium)

        Spacer(modifier = Modifier.height(6.dp))

        //Answer choice
        question.options.forEachIndexed { i, option ->
            val isCorrect = option == question.answer
            val isSelected = if (showAnswer) {
                option == question.answer
            } else {
                selected == option
            }

            val color = when {
                showAnswer && isSelected && isCorrect -> Color(0xFF4CAF50)
                isSelected -> Color(0xFF2196F3)
                else -> Color.Transparent
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                //Radio button
                RadioButton(
                    selected = isSelected,
                    onClick = {
                        viewModel.selectAnswer(question.id, option)
                    },
                    colors = RadioButtonDefaults.colors(color)
                )
                //Radio label
                Text(
                    "${i+1}. ${option}.",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        viewModel.selectAnswer(question.id, option)
                    })
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        //Navigation Button Back, Speak, Next
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //Back button
            Button(onClick = { if (index > 0) viewModel.currentIndex.value-- },
                enabled = index > 0
                ) {
                Text(text = "ย้อนกลับ", fontSize = 16.sp)
            }

            //Speak button
            Button(
                onClick = {
                    val speakText = buildString {

                        if (showAnswer) {
                            append(question.thai)
                        } else {
                            append("Questions: ${question.question}. ")
                            question.options.forEachIndexed { i, option ->
                                append(" ${i+1}. ${option}. ")
                            }
                        }
                    }
                    tts.language = if (showAnswer) java.util.Locale("th","TH")
                    else java.util.Locale.US
                    tts.speak(speakText, TextToSpeech.QUEUE_FLUSH, null, null)
                }
            ) {
                Text("พูด", fontSize = 16.sp)
            }

            //Next button
            Button(onClick = {
                if (index < questions.lastIndex) {
                    viewModel.currentIndex.value++
                    viewModel.showAnswer.value = false
                } else {
                    onFinish()
                }
            }) {
                Text( if (index == questions.lastIndex) "เสร็จ" else "ถัดไป",
                    fontSize = 16.sp)

                }
        }

        Spacer(modifier = Modifier.height(8.dp))

        //Switch for Show Answer
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Switch(
                checked = showAnswer,
                onCheckedChange = {viewModel.showAnswer.value = it}
            )
            Spacer( modifier = Modifier.width(10.dp))
            Text(
                "เฉลย",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black)
        }

        Spacer(modifier = Modifier.height(8.dp))
        //Show correct answer
        if (showAnswer) {
            Text(text = question.thai,
                fontSize = 22.sp,
                color = Color.Blue,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(64.dp))
    }

}