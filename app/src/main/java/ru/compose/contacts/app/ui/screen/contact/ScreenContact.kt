package ru.compose.contacts.app.ui.screen.contact

import android.provider.ContactsContract.CommonDataKinds.Phone
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import ru.compose.contacts.app.data.model.Contact
import ru.compose.contacts.app.ui.theme.ComposeContactsTheme
import ru.compose.contacts.app.utils.dpToSp

@Composable
fun ScreenContact(
    viewModel: ViewModelContact = ViewModelContact(),
    contact: Contact,
    onNavigate: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                modifier = Modifier
                    .padding(top = 16.dp, start = 8.dp)
                    .size(48.dp),
                onClick = {
                    onNavigate("main")
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colors.primary
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )

            IconButton(
                modifier = Modifier
                    .padding(top = 16.dp, end = 8.dp)
                    .size(48.dp),
                onClick = {

                }) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit",
                    tint = MaterialTheme.colors.primary
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(192.dp)
                .height(192.dp)
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colors.secondary,
                    shape = CircleShape
                )
        ) {
            if (contact.photo == null) {
                Text(
                    text = contact.letter,
                    fontSize = dpToSp(dp = 44.dp),
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
            fontSize = 22.sp,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(16.dp)
        )

        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 100.dp)
                .padding(start = 16.dp, end = 16.dp, bottom = 32.dp)
            //.padding(16.dp)
        ) {
            Column {

                Text(
                    text = "Контактная информация",
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
                )

                contact.phones.forEach { phone ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Phone,
                            contentDescription = "Edit",
                            tint = MaterialTheme.colors.primary
                        )

                        val type = when (phone.type.toInt()) {
                            Phone.TYPE_HOME -> "\nДомашний"
                            Phone.TYPE_MOBILE -> "\nМобильный"
                            Phone.TYPE_WORK -> "\nРабочий"
                            else -> ""
                        }

                        Text(
                            text = phone.num+type,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.fillMaxWidth()
                    .height(16.dp))

            }

        }
    }

    BackHandler {
        onNavigate("main")
    }
}

@Preview
@Composable
private fun Preview() {
    ComposeContactsTheme {
        ScreenContact(
            contact = Contact("A", "A", "A", "A", "A", "A", mutableListOf())
        ) {}
    }
}