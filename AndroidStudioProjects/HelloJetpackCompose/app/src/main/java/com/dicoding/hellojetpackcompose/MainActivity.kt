package com.dicoding.hellojetpackcompose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.hellojetpackcompose.ui.theme.HelloJetpackComposeTheme

private val sampleName =
    listOf(
        "Andre",
        "Desta",
        "Parto",
        "Wendy",
        "Komeng",
        "Raffi Ahmad",
        "Andhika Pratama",
        "Vincent Ryan Rompies",
        "Vincent Ryan Rompies",
        "Vincent Ryan Rompies",
        "Vincent Ryan Rompies",
    )

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent { HelloJetpackComposeTheme { HelloJetpackComposeApp() } }
  }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HelloJetpackComposeAppPreview() {
  HelloJetpackComposeTheme { HelloJetpackComposeApp() }
}

@Composable
fun HelloJetpackComposeApp() {
  Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
    GreetingList(sampleName)
  }
}

@Composable
fun GreetingList(names: List<String>) {
  if (names.isNotEmpty()) {
    LazyColumn { items(names) { name -> Greeting(name) } }
  } else {
    Text("No People to greet :(")
  }
}

@Composable
fun Greeting(name: String) {
  var isExpanded by remember { mutableStateOf(false) }
  val animatedSizeDp by
      animateDpAsState(
          targetValue = if (isExpanded) 120.dp else 80.dp,
          animationSpec =
              spring(
                  dampingRatio = Spring.DampingRatioMediumBouncy,
                  stiffness = Spring.StiffnessLow,
              ),
      )

  Card(
      backgroundColor = MaterialTheme.colors.primary,
      shape = MaterialTheme.shapes.medium,
      modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
  ) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
      Image(
          painter = painterResource(R.drawable.jetpack_compose),
          contentDescription = "Logo Jetpack Compose",
          modifier = Modifier.size(animatedSizeDp),
      )
      Spacer(modifier = Modifier.width(8.dp))
      Column(modifier = Modifier.weight(1f)) {
        Text(text = "Welcome to Dicoding!")
        Text(
            text = "Hello $name!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
      }
      IconButton(onClick = { isExpanded = !isExpanded }) {
        Icon(
            imageVector =
                if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = if (isExpanded) "Show more" else "Show less",
        )
      }
    }
  }
}
