package com.dicoding.githubusercompose.ui.detail_user.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
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
fun DetailUserFollow(
    modifier: Modifier = Modifier,
    followers: Int = 42,
    following: Int = 42,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_people_24),
            contentDescription = stringResource(R.string.followers_and_following),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(stringResource(R.string.followers_count, followers))
        Icon(
            painter = painterResource(R.drawable.baseline_circle_24),
            contentDescription = stringResource(R.string.followers_and_following),
            modifier = Modifier.size(8.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(stringResource(R.string.following_count, following))
    }
}

@Preview(name = "DetailUserFollow", showBackground = true)
@Composable
private fun PreviewDetailUserFollow() {
    DetailUserFollow()
}