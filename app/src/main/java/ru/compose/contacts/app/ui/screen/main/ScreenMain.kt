package ru.compose.contacts.app.ui.screen.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import ru.compose.contacts.app.R
import ru.compose.contacts.app.data.model.Contact
import ru.compose.contacts.app.ui.screen.main.components.Toolbar
import ru.compose.contacts.app.ui.theme.ComposeContactsTheme
import ru.compose.contacts.app.utils.dpToSp

@Composable
fun ScreenMain(
    viewModel: ViewModelMain = ViewModelMain(),
    onNavigateToContact: (Contact) -> Unit
) {
    Column {
        Toolbar(viewModel)
        Content(viewModel, onNavigateToContact)
    }
}

@Composable
private fun Content(viewModel: ViewModelMain, onClickContact: (Contact) -> Unit) {
    Log.d("develop", ": Content: ")
    val contacts = viewModel.contacts.collectAsState()
    when (contacts.value.isEmpty()) {
        true -> ContentEmpty()
        else -> ContentData(contacts.value, onClickContact)
    }
}

@Composable
private fun ContentEmpty() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.scr_contacts_message_empty),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(32.dp)
        )
    }
}

@Composable
private fun ContentData(data: List<Contact>, onClickContact: (Contact) -> Unit) {

    Log.d("develop", ": ContentData: ")

    LazyColumn {
        items(data.size) { position ->
            val contact = data[position]

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clickable {
                        onClickContact(contact)
                    }) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp)
                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colors.secondary,
                            shape = CircleShape
                        )
                ) {
                    if (contact.photo == null) {
                        Text(
                            text = contact.letter,
                            fontSize = dpToSp(dp = 20.dp),
                            fontWeight = FontWeight.Medium
                        )
                    } else {

                        GlideImage(
                            imageModel = { contact.photo }, // loading a network image using an URL.
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.FillBounds,
                                alignment = Alignment.Center
                            ),
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }
                }

                Text(
                    text = contact.displayName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, end = 16.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_5")
private fun PreviewScreen() {
    ComposeContactsTheme {
        ScreenMain() {}
    }
}