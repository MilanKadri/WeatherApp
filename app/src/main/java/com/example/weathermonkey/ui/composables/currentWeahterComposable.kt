package com.example.weathermonkey.ui.composables

import WeatherModel
import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathermonkey.R
import com.example.weathermonkey.data.repository.mockData.mockResponse
import com.example.weathermonkey.ui.theme.fontColors
import com.example.weathermonkey.utils.indexedTempForCurrentHourAsString

@Composable
fun CurrentWeatherComposable(
    modifier: Modifier = Modifier,
    data: WeatherModel
) {
    Card(
        modifier = Modifier.padding(6.dp).height(250.dp),
        colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha = 0.15f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround,

            ) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp),
                painter = painterResource(id = R.drawable.rainicon),
                contentDescription = "",
                contentScale = ContentScale.FillHeight,
            )

            Text(
                text = "${indexedTempForCurrentHourAsString.getCurrent()}°",
                color = Color.White,
                fontSize = 100.sp
            )
        }
    }
}

@Preview(
    name = "CurrentWeatherComp",
    device = "id:pixel_7_pro",
    showSystemUi = false,
    backgroundColor = 0xFF1BC5F1,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true
)
@Composable
private fun CurrentWeatherComposablePreview() {
    CurrentWeatherComposable(data = mockResponse)
}


