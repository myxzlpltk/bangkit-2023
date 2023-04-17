package com.dicoding.githubusercompose.ui.dashboard.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.dicoding.githubusercompose.R
import com.dicoding.githubusercompose.ui.dashboard.LocalDashboardActions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardSearchView(
    modifier: Modifier = Modifier,
    value: TextFieldValue = TextFieldValue(""),
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val actions = LocalDashboardActions.current

    TextField(
        value = value,
        onValueChange = { textField ->
            actions.updateSearch(textField.copy(text = textField.text.lowercase()))
        },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
        singleLine = true,
        shape = RectangleShape,
        placeholder = {
            Text(
                text = stringResource(R.string.dashboard_search_hint),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            focusManager.clearFocus()
            focusRequester.freeFocus()
        }),
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.onPrimaryContainer,
            cursorColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )

    LaunchedEffect(true) {
        focusRequester.requestFocus()
    }
}

@Preview(name = "DashboardSearchView", showBackground = true)
@Composable
private fun PreviewDashboardSearchView() {
    DashboardSearchView()
}