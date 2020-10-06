package ca.ciccc.carlos.contacts.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.ciccc.carlos.contacts.network.Contact
import ca.ciccc.carlos.contacts.network.ContactApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val maxContact = 30

private var contactDB: List<Contact>? = null

class ContactsViewModel : ViewModel() {

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val myContactsList = MutableLiveData<List<Contact>>()
    val contactsList: LiveData<List<Contact>>
        get() = myContactsList

    private val myNavigateToAddContact = MutableLiveData<Boolean>()
    val navigateToAddContact: LiveData<Boolean>
        get() = myNavigateToAddContact

    init {
        contactDB?.let { myContactsList.value = it } ?: getContacts(maxContact)
    }

    private fun getContacts(number: Int) {
        coroutineScope.launch {
            val getContactsDeferred = ContactApi.retrofitService.getContactsAsync(number)
            try {
                val contactList = getContactsDeferred.await().contactList
                myContactsList.value = contactList
                contactDB = contactList
            } catch (e: Exception) {
                myContactsList.value = ArrayList()
            }
        }
    }

    fun addContact(contact: Contact) {
        myContactsList.value?.let {
            myContactsList.value = it + contact
            contactDB = myContactsList.value
        }
    }

    fun onAddContactClicked() {
        myNavigateToAddContact.value = true
    }

    fun onAddContactNavigated() {
        myNavigateToAddContact.value = null
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}