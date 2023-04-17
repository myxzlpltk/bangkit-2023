package com.dicoding.githubusercompose.ui.about_me

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.dicoding.githubusercompose.R
import com.dicoding.githubusercompose.ui.shared.ErrorScreen
import com.dicoding.githubusercompose.ui.shared.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutMeScreen(
    state: AboutMeState = AboutMeState.Loading,
    actions: AboutMeActions = AboutMeActions()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = { Text(stringResource(R.string.about_me)) },
                navigationIcon = {
                    IconButton(onClick = actions.navigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back)
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()

        when (state) {
            AboutMeState.Loading -> LoadingScreen(modifier = Modifier.padding(innerPadding))
            is AboutMeState.Error -> ErrorScreen(
                modifier = Modifier.padding(innerPadding),
                message = state.message,
            )

            is AboutMeState.Success -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(innerPadding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape),
                    model = state.user.avatarUrl,
                    contentDescription = stringResource(
                        R.string.avatar_description,
                        "myxzlpltk"
                    ),
                    contentScale = ContentScale.Crop,
                    loading = {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(48.dp)
                        )
                    },
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = state.user.name ?: stringResource(R.string.no_name),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "myxzlpltk@gmail.com",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(24.dp))
                FilledTonalButton(onClick = { actions.navigateToGithub(state.user.login) }) {
                    Text("Check My Github")
                }
            }
        }
    }
}

@Composable
@Preview(name = "AboutMe")
private fun AboutMeScreenPreview() {
    AboutMeScreen()
}

