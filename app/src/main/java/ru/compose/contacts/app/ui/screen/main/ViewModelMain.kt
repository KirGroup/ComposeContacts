package ru.compose.contacts.app.ui.screen.main

import android.provider.ContactsContract
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.compose.contacts.app.App
import ru.compose.contacts.app.data.mapper.CursorToContactMapper
import ru.compose.contacts.app.data.model.Contact
import ru.compose.contacts.app.data.model.ToolbarState

class ViewModelMain : ViewModel() {

    private val _toolbarState = MutableStateFlow(ToolbarState.TITLE)
    val toolbarState: StateFlow<ToolbarState> = _toolbarState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _contacts = MutableStateFlow<List<Contact>>(listOf())
    val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()

    private var searchJob: Job? = null

    init {
        updateData("")
    }

    fun applyToolbarState(state: ToolbarState) {
        _toolbarState.update { state }
    }

    fun setQuery(query: String) {
        _query.update { query }
        updateData(query)
    }

    fun updateData(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            val data = loadContacts(query)
            withContext(Dispatchers.Main) {
                _contacts.update { data }
                searchJob = null
            }
        }
    }

    private fun loadContacts(query: String): List<Contact> {

        val resultMap = mutableMapOf<String, Contact>()

        val selection = if (query.isEmpty()) null else "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} LIKE '%$query%'"
        val selectionArgs = if (query.isEmpty()) null else arrayOf(query)

        val cursor = App.app.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, selection, null, null
        )

        cursor?.let {
            if (it.moveToFirst()) {
                var counter = 0
                val mapper = CursorToContactMapper(cursor)
                do {
                    if (counter == 0) {
                        //Log.v("develop", "#${counter}: ${DatabaseUtils.dumpCursorToString(cursor)}")
                    }
                    counter++
                    val contact = mapper.mapToContact(cursor)
                    if (!resultMap.contains(contact.contactId)) {
                        resultMap.put(contact.contactId, contact)
                    } else {
                        resultMap[contact.contactId]!!.phones.addAll(contact.phones)
                    }
                } while (it.moveToNext())
            }

            cursor.close()
        }

        val resultList = resultMap.values.toMutableList()
        resultList.sortBy { it.displayName }

        return resultList
    }
}