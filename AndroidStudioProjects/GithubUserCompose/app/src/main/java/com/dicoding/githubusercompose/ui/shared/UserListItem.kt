package com.dicoding.githubusercompose.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.dicoding.githubusercompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListItem(
    modifier: Modifier = Modifier,
    login: String,
    imageUrl: String,
    navigateToDetail: (String) -> Unit,
) {
    ListItem(
        modifier = modifier.clickable { navigateToDetail(login) },
        headlineText = {
            Text(login, style = MaterialTheme.typography.titleLarge)
        },
        leadingContent = {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                model = imageUrl,
                contentDescription = stringResource(R.string.avatar_description, login),
                contentScale = ContentScale.Crop,
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(8.dp)
                    )
                },
            )
        }
    )
}