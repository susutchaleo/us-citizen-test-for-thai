package com.susutchaleo.ustestforthai.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.susutchaleo.ustestforthai.R


@Composable
fun HomeScreen(onStartClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A1F3D))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image
            Image(
                painter = painterResource(R.drawable.home),
                contentDescription = "US Citizen Test for Thai",
                modifier = Modifier
                    .size(250.dp)
                    .padding(bottom = 24.dp)
            )

            Text(
                "ยินดีต้อนรับเข้าสู่",
                fontSize = 22.sp,
                color = Color(0xFFECC1A4),
                fontWeight = FontWeight.Bold
            )
            Text(
                "แบบทดสอบ",
                fontSize = 22.sp,
                color = Color(0xFFCBAACB),
                fontWeight = FontWeight.Bold,
            )

            Text(
                "การเป็นพลเมืองอเมริกา",
                fontSize = 22.sp,
                color = Color(0xFFCBAACB),
                fontWeight = FontWeight.Bold,
                )

            Text(
                "สำหรับคนไทย",
                fontSize = 22.sp,
                color = Color(	0xFFD8BFD8),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            //button
            Button(onClick = { onStartClick() }) {
                Text("เริ่มทดสอบ",
                        fontSize = 18.sp,
                    color = Color(0xFFFFFFFF),
                    fontWeight = FontWeight.Bold
                    )
            }

            // Disclaimer

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "คำเตือน: แอปนี้ไม่ใช่แอปทางการของรัฐบาลสหรัฐฯ และไม่มีความเกี่ยวข้องกับหน่วยงานของรัฐใด ๆ",
                fontSize = 12.sp,
                color = Color.LightGray,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Disclaimer: This app is not affiliated with the U.S. government. It is provided for educational purposes only.",
                fontSize = 12.sp,
                color = Color.LightGray,
                lineHeight = 16.sp
            )
        }
    }
}



