package ru.compose.contacts.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.compose.contacts.app.utils.dpToSp

@Preview(showBackground = true)
@Composable
fun Demo() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "DEMO",
            fontSize = dpToSp(96.dp),
            fontWeight = FontWeight.Bold,
            color = Color(0X22000000),
            modifier = Modifier
                .align(Alignment.Center)
                .rotate(-45f)
        )
    }
}