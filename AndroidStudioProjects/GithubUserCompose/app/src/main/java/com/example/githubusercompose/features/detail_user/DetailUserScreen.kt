package com.example.githubusercompose.features.detail_user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.githubusercompose.R
import com.example.githubusercompose.data.entities.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailUserScreen(
    state: DetailUserState = DetailUserState.Loading,
    actions: DetailUserActions = DetailUserActions()
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
                title = {
                    Text(stringResource(R.string.detail_user_title))
                },
                navigationIcon = {
                    IconButton(onClick = actions.navigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_up)
                        )
                    }
                },
                actions = {
                    when (state) {
                        is DetailUserState.Success -> {
                            IconButton(onClick = actions.toggleFavorite) {
                                Icon(
                                    imageVector = if (state.user.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = stringResource(R.string.dashboard_close)
                                )
                            }
                        }

                        else -> {}
                    }
                }
            )
        }
    ) { innerPadding ->
        when (state) {
            DetailUserState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.cannot_fetch_user_data))
                }
            }

            DetailUserState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is DetailUserState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    /* User info */
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        /* Avatar image */
                        SubcomposeAsyncImage(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape),
                            model = state.user.avatarUrl,
                            contentDescription = stringResource(
                                R.string.avatar_description,
                                state.user.login
                            ),
                            contentScale = ContentScale.Crop,
                            loading = {
                                CircularProgressIndicator(modifier = Modifier.padding(18.dp))
                            },
                        )

                        /* Name and username */
                        Column {
                            Text(
                                text = state.user.name ?: stringResource(R.string.no_name),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            Text(
                                text = "@${state.user.login}",
                                style = MaterialTheme.typography.bodyLarge,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }

                    /* Bio */
                    Text(
                        text = state.user.bio ?: stringResource(R.string.no_bio),
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    /* Followers and Following */
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_people_24),
                            contentDescription = stringResource(R.string.followers_and_following),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(stringResource(R.string.followers_count, state.user.followers))
                        Icon(
                            painter = painterResource(R.drawable.baseline_circle_24),
                            contentDescription = stringResource(R.string.followers_and_following),
                            modifier = Modifier.size(8.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(stringResource(R.string.following_count, state.user.following))
                    }

                    /* Repository */
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_apps_24),
                            contentDescription = stringResource(R.string.repositories),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(stringResource(R.string.repositories_count, state.user.publicRepos))
                    }
                }
            }
        }
    }
}

@Composable
@Preview(name = "DetailUser", showBackground = true, device = Devices.PIXEL_4)
private fun DetailUserScreenPreview() {
    DetailUserScreen(
        state = DetailUserState.Success(
            user = User(
                id = 1,
                login = "myxzlpltk",
                name = "Saddam Azy",
                avatarUrl = "https://avatars.githubusercontent.com/u/59659668?v=4",
                bio = "Android Developer with Kotlin and Flutter. Used to tackle in fullstack web. Also, do some nice Computer Vision and Data Science.",
                publicRepos = 25,
                following = 27,
                followers = 28,
                isFavorite = false,
            ),
        )
    )
}

