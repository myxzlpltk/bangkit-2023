package com.dicoding.jetcoffee.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.jetcoffee.ui.theme.JetCoffeeTheme

@Composable
fun SectionText(
    title: String,
    modifier: Modifier = Modifier,
) {
  Text(
      text = title,
      style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.ExtraBold),
      modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
  )
}

@Preview(showBackground = true)
@Composable
fun SectionTextPreview() {
  JetCoffeeTheme {
    SectionText(
        title = "Section Title",
        modifier = Modifier.padding(8.dp),
    )
  }
}
