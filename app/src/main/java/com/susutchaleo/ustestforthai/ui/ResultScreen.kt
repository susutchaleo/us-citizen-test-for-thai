package com.susutchaleo.ustestforthai.ui


import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.susutchaleo.ustestforthai.QuizViewModel
import com.susutchaleo.ustestforthai.R


@SuppressLint("ContextCastToActivity")
@Composable
fun ResultScreen(viewModel: QuizViewModel, onStartClick: () -> Unit) {
    val score by viewModel.score.collectAsState()
    val questionsList by viewModel.questions.collectAsState()
    val total = questionsList.size
    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ){

        Spacer(modifier = Modifier.height(50.dp))
        Image(
           painter = painterResource(R.drawable.banner_result),
            contentDescription = "Banner",
            modifier = Modifier
                .height(250.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text("คุณได้ทำแบบทดสอบ",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD8BFD8)
        )

        Text("เรียบร้อย!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD8BFD8)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("คุณได้คะแนน: $score/$total",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Row() {
            Button(onClick = {
                viewModel.resetQuiz()
                onStartClick()
            }) {
                Text("เริ่มใหม่", fontSize = 16.sp)
            }

            //
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                val activity = context as? Activity
                activity?.finish()
            }) {
                Text("ออกจากโปรแกรม", fontSize = 16.sp)
            }
        }

    }
}