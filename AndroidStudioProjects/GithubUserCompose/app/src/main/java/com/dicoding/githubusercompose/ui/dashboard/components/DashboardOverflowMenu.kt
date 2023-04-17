package com.dicoding.githubusercompose.ui.dashboard.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.dicoding.githubusercompose.R
import com.dicoding.githubusercompose.ui.dashboard.LocalDashboardActions

@Composable
fun DashboardOverflowMenu(
    modifier: Modifier = Modifier,
    overflowMenuVisible: Boolean = false,
    navigateToFavorites: () -> Unit = {},
    navigateToAbout: () -> Unit = {}
) {
    val actions = LocalDashboardActions.current

    IconButton(
        modifier = modifier,
        onClick = actions.toggleOverflowMenu
    ) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.expand_menu)
        )
    }
    DropdownMenu(
        expanded = overflowMenuVisible,
        onDismissRequest = actions.toggleOverflowMenu
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.favorite_users)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = stringResource(R.string.favorite_users)
                )
            },
            onClick = navigateToFavorites
        )
        DropdownMenuItem(
            text = { Text(stringResource(R.string.about_me)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = stringResource(R.string.about_me)
                )
            },
            onClick = navigateToAbout
        )
    }
}

@Preview(name = "DashboardOverflowMenu", showBackground = true)
@Composable
private fun PreviewDashboardOverflowMenu() {
    DashboardOverflowMenu()
}