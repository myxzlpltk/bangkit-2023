package com.example.githubusercompose.features.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import com.example.githubusercompose.R
import com.example.githubusercompose.components.BackPressHandler
import com.example.githubusercompose.data.entities.User
import com.example.githubusercompose.features.dashboard.components.DashboardList
import com.example.githubusercompose.features.dashboard.components.DashboardSearchView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    state: DashboardState = DashboardState(),
    actions: DashboardActions = DashboardActions(),
    pager: LazyPagingItems<User>
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
                    if(state.search) {
                        DashboardSearchView(
                            value = state.query,
                            onValueChange = actions.onValueChange,
                        )
                    } else {
                        Text(stringResource(R.string.dashboard_title))
                    }
                },
                navigationIcon = {
                    if (state.search) {
                        IconButton(onClick = actions.closeSearch) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.dashboard_close)
                            )
                        }
                    }
                },
                actions = {
                    if (state.search) {
                        if (state.query.text.isNotEmpty()) {
                            IconButton(onClick = actions.clearSearch) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = stringResource(R.string.dashboard_clear)
                                )
                            }
                        }
                    } else {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = stringResource(R.string.dashboard_favorite)
                            )
                        }
                        IconButton(onClick = actions.openSearch) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = stringResource(R.string.dashboard_search)
                            )
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        DashboardList(
            modifier = Modifier.padding(innerPadding),
            pager = pager
        )
    }

    BackPressHandler {
        if (state.search) {
            actions.closeSearch()
        }
    }
}