package ru.compose.contacts.app.data.model

data class Contact(
    val contactId: String,
    val displayName: String,
    val firstName: String,
    val lastName: String,
    val letter: String,
    val photo: String?,
    val phones: MutableList<Phone> = mutableListOf()
) {
    var raw = ""

    data class Phone(
        val num: String,
        val type: String
    )
}