package ru.compose.contacts.app.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import ru.compose.contacts.app.data.model.Contact
import ru.compose.contacts.app.ui.screen.main.ScreenMain
import ru.compose.contacts.app.ui.components.ScreenPermissions
import ru.compose.contacts.app.ui.screen.contact.ScreenContact
import ru.compose.contacts.app.ui.theme.ComposeContactsTheme

@OptIn(ExperimentalPermissionsApi::class)
class ActivityMain : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeContactsTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {

                    val navController = remember { mutableStateOf("main") }
                    val contactsPermissionState = rememberMultiplePermissionsState(
                        permissions = listOf(
                            android.Manifest.permission.READ_CONTACTS,
                            android.Manifest.permission.WRITE_CONTACTS
                        )
                    )
                    when (contactsPermissionState.allPermissionsGranted) {
                        true -> when (navController.value) {
                            "main" -> ScreenMain() {
                                contact = it
                                navController.value = "contact"
                            }
                            "contact" -> ScreenContact(contact = contact) { destination ->
                                navController.value = destination
                            }
                        }

                        /*NavHost(navController = navController, startDestination = "main") {
                            composable("main") {
                                ScreenMain() {
                                    contact = it
                                    navController.navigate("contact")
                                }
                            }

                            composable("contact") { ScreenContact(navController = navController) }
                        }*/
                        else -> ScreenPermissions(contactsPermissionState)
                    }
                }
            }
        }
    }

    companion object {
        lateinit var contact: Contact
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_5")
@Composable
fun DefaultPreview() {
    ComposeContactsTheme {
        //ScreenMain()
    }
}