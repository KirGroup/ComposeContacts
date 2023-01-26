package ru.compose.contacts.app.ui.screen.main.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.compose.contacts.app.R
import ru.compose.contacts.app.data.model.ToolbarState
import ru.compose.contacts.app.ui.screen.main.ViewModelMain
import ru.compose.contacts.app.ui.theme.ComposeContactsTheme

@Composable
fun Toolbar(viewModel: ViewModelMain) {

    val state = viewModel.toolbarState.collectAsState()

    when (state.value) {
        ToolbarState.TITLE -> ToolbarTitle {
            viewModel.applyToolbarState(ToolbarState.SEARCH)
        }
        ToolbarState.SEARCH -> ToolbarSearch(
            query = viewModel.query,
            onClickBack = {
                viewModel.setQuery("")
                viewModel.applyToolbarState(ToolbarState.TITLE)
            },
            onTextChanged = { text ->
                viewModel.setQuery(text)
            })
    }

    BackHandler {
        if (state.value == ToolbarState.SEARCH) {
            viewModel.setQuery("")
            viewModel.applyToolbarState(ToolbarState.TITLE)
        }
    }
}

@Composable
fun ToolbarTitle(onClickSearch: () -> Unit) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            color = MaterialTheme.colors.onSurface,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .weight(1f)
        )

        IconButton(onClick = onClickSearch) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                tint = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
fun ToolbarSearch(query: StateFlow<String>, onClickBack: () -> Unit, onTextChanged: (text: String) -> Unit) {
    val text = query.collectAsState()

    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface
    ) {

        IconButton(
            onClick = onClickBack
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colors.primary
            )
        }

        TextField(
            value = text.value,
            onValueChange = { onTextChanged(it) },
            placeholder = {
                Text(
                    text = stringResource(
                        id = R.string.scr_contacts_search_hint
                    )
                )
            },
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp
            ),
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onSurface,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            singleLine = true
        )

        if (text.value.isNotEmpty()) {
            IconButton(onClick = {
                onTextChanged("")
            }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Clear",
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewToolbarTitle() {
    ComposeContactsTheme {
        ToolbarTitle {}
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewToolbarSearch() {
    ComposeContactsTheme {
        ToolbarSearch(MutableStateFlow(""), {}, {})
    }
}