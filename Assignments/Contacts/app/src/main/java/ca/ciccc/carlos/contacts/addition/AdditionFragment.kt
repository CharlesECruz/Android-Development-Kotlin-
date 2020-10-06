package ca.ciccc.carlos.contacts.addition

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import ca.ciccc.carlos.contacts.databinding.FragmentAdditionBinding
import ca.ciccc.carlos.contacts.network.Contact
import java.util.*

class AdditionFragment : Fragment() {

    private val viewModel: AdditionViewModel by lazy {
        ViewModelProviders.of(this).get(AdditionViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAdditionBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.navigateToContactList.observe(this, Observer {
            it?.let {
                this.findNavController().navigate(
                    AdditionFragmentDirections.actionAdditionFragmentToContactsFragment(it)
                )
                viewModel.onSubmitNavigated()
            }
        })

        binding.buttonSubmitAddContact.setOnClickListener {
            val tokens = StringTokenizer(binding.editTextName.text.toString(), " ")
            val cellNum = binding.editTextPhoneNumber.text.toString()
            viewModel.onSubmitClicked(
                Contact.generateContact(
                    tokens.nextToken().capitalize(),
                    tokens.nextToken().capitalize(),
                    cellNum.substring(0, 3) + "-"
                            + cellNum.substring(3, 6) + "-"
                            + cellNum.substring(6)
                )
            )
            val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        }

        binding.editTextName.doAfterTextChanged {
            checkAndSetSubmitEnabled(binding)
        }

        binding.editTextPhoneNumber.doAfterTextChanged {
            checkAndSetSubmitEnabled(binding)
        }

        return binding.root
    }

    private fun checkAndSetSubmitEnabled(binding: FragmentAdditionBinding) {
        binding.buttonSubmitAddContact.isEnabled =
            Contact.isNameAvailable(binding.editTextName.text.toString()) &&
                    Contact.isCellAvailable(
                        binding.editTextPhoneNumber.text.toString()
                    )
    }
}
