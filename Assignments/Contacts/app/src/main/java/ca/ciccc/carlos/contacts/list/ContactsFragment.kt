package ca.ciccc.carlos.contacts.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import ca.ciccc.carlos.contacts.R
import ca.ciccc.carlos.contacts.databinding.FragmentContactsBinding

class ContactsFragment : Fragment() {

    private val viewModel: ContactsViewModel by lazy {
        ViewModelProviders.of(this).get(ContactsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentContactsBinding.inflate(inflater)
        val adapter = ContactAdapter()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.contactList.adapter = adapter

        viewModel.contactsList.observe(this, Observer {
            it?.let {
                adapter.addIndexAndSubmitList(it)
            }
        })

        viewModel.navigateToAddContact.observe(this, Observer {
            it?.let {
                this.findNavController().navigate(
                    ContactsFragmentDirections.actionContactsFragmentToAdditionFragment())
                viewModel.onAddContactNavigated()
            }
        })

        arguments?.let {
            viewModel.addContact(ContactsFragmentArgs.fromBundle(arguments!!).newContact)
            arguments = null
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_item, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_add_contact -> {
                viewModel.onAddContactClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
