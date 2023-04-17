package com.dicoding.githubusercompose.ui.dashboard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dicoding.githubusercompose.R
import com.dicoding.githubusercompose.data.entities.User
import com.dicoding.githubusercompose.ui.dashboard.components.DashboardList
import com.dicoding.githubusercompose.ui.dashboard.components.DashboardOverflowMenu
import com.dicoding.githubusercompose.ui.dashboard.components.DashboardSearchView
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    state: DashboardState = DashboardState(),
    actions: DashboardActions = DashboardActions(),
    pager: LazyPagingItems<User> = flowOf(PagingData.empty<User>()).collectAsLazyPagingItems(),
    listState: LazyListState = rememberLazyListState(),
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
                    if (state.searchVisible) {
                        ProvideDashboardActions(actions) {
                            DashboardSearchView(value = state.query)
                        }
                    } else {
                        Text(stringResource(R.string.dashboard_title))
                    }
                },
                navigationIcon = {
                    if (state.searchVisible) {
                        IconButton(onClick = actions.toggleSearch) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.dashboard_close)
                            )
                        }
                    }
                },
                actions = {
                    if (state.searchVisible) {
                        if (state.query.text.isNotEmpty()) {
                            IconButton(onClick = actions.clearSearch) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = stringResource(R.string.dashboard_clear)
                                )
                            }
                        }
                    } else {
                        IconButton(onClick = actions.toggleSearch) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = stringResource(R.string.dashboard_search)
                            )
                        }
                        ProvideDashboardActions(actions) {
                            DashboardOverflowMenu(
                                overflowMenuVisible = state.overflowMenuVisible,
                            )
                        }
                    }
                }
            )
        },
    ) { innerPadding ->
        ProvideDashboardActions(actions) {
            DashboardList(
                modifier = Modifier.padding(innerPadding),
                pager = pager,
                listState = listState,
            )
        }

        LaunchedEffect(state.query.text) {
            if (pager.itemCount > 0) {
                listState.scrollToItem(0)
            }
        }
    }

    BackHandler(state.searchVisible) {
        actions.toggleSearch()
    }
}

@Composable
@Preview(name = "Dashboard")
private fun DashboardScreenPreview() {
    DashboardScreen()
}

