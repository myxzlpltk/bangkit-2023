package com.dicoding.githubusercompose.ui.detail_user.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dicoding.githubusercompose.R

@Composable
fun DetailUserPublicRepos(
    modifier: Modifier = Modifier,
    publicRepos: Int = 42,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_apps_24),
            contentDescription = stringResource(R.string.repositories),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(stringResource(R.string.repositories_count, publicRepos))
    }
}

@Preview(name = "DetailUserPublicRepos", showBackground = true)
@Composable
private fun PreviewDetailUserPublicRepos() {
    DetailUserPublicRepos()
}