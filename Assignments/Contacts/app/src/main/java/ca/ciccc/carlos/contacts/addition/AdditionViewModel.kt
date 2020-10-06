package ca.ciccc.carlos.contacts.addition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.ciccc.carlos.contacts.network.Contact

class AdditionViewModel : ViewModel() {

    private val myNavigateToContactList = MutableLiveData<Contact>()
    val navigateToContactList: LiveData<Contact>
        get() = myNavigateToContactList

    fun onSubmitClicked(contact: Contact) {
        myNavigateToContactList.value = contact
    }

    fun onSubmitNavigated() {
        myNavigateToContactList.value = null
    }

}
