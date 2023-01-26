package ru.compose.contacts.app.data.mapper

import android.database.Cursor
import android.provider.ContactsContract
import ru.compose.contacts.app.data.model.Contact

class CursorToContactMapper(cursor: Cursor) {

    val colDisplayName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
    val colPhone = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
    val colPhoneType = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)
    val colPhoto = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)
    val colContactId = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)

    fun mapToContact(cursor: Cursor): Contact {

        val phone = cursor.getString(colPhone)
        val phoneType = cursor.getString(colPhoneType)
        val photo = cursor.getString(colPhoto)
        val contactId = cursor.getString(colContactId)
        val displayName = cursor.getString(colDisplayName)

        var firstName = "???"
        var lastName = "???"
        var letter = "?"

        try {
            val nameArr = displayName.split("\\s".toRegex())
            firstName = nameArr[0].trim()
            lastName = if (nameArr.size > 1) nameArr[1].trim() else "---"
            letter = firstName.substring(0, 1)
        } catch (_: Exception) {
        }

        return Contact(
            contactId,
            displayName,
            firstName,
            lastName,
            letter,
            photo,
            mutableListOf(Contact.Phone(phone, phoneType))
        ).apply {
            //raw = DatabaseUtils.dumpCursorToString(cursor)
        }
    }

}