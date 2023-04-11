package com.dicoding.mytemperatureconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dicoding.mytemperatureconverter.ui.theme.MyTemperatureConverterTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MyTemperatureConverterTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          StatefulTemperatureInput()
        }
      }
    }
  }
}

@Composable
fun StatefulTemperatureInput(
    modifier: Modifier = Modifier,
) {
  var input by rememberSaveable { mutableStateOf("") }
  var output by rememberSaveable { mutableStateOf("") }
  Column(modifier.padding(16.dp)) {
    Text(
        text = stringResource(R.string.stateful_converter),
        style = MaterialTheme.typography.h5,
    )
    OutlinedTextField(
        value = input,
        label = { Text(stringResource(R.string.enter_celsius)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = { newValue ->
            input = newValue
            output = convertToFahrenheit(newValue)
        },
    )
    Text(stringResource(R.string.temperature_fahrenheit, output))
  }
}

private fun convertToFahrenheit(celsius: String) =
    celsius.toDoubleOrNull()?.let {
        (it * 9 / 5) + 32
    }.toString()