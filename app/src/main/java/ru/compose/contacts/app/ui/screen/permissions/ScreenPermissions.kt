package ru.compose.contacts.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import ru.compose.contacts.app.R
import ru.compose.contacts.app.ui.theme.ComposeContactsTheme

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun ScreenPermissions(state: MultiplePermissionsState?) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.scr_permissions_message),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(32.dp)
        )

        Button(
            modifier = Modifier.height(48.dp),
            onClick = {
                state?.launchMultiplePermissionRequest()
            }) {
            Text(stringResource(id = R.string.scr_permissions_btn_provide))
        }
    }
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_5")
private fun Preview() {
    ComposeContactsTheme {
        ScreenPermissions(null)
    }
}