package com.example.samsung_coursework.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.samsung_coursework.R
import com.example.samsung_coursework.ui.view_model.ProfileViewModel

class FragmentProfileAuthorised : Fragment() {
    private val viewModel: ProfileViewModel by activityViewModels()
    private lateinit var buttonSignOut: Button
    private lateinit var textNickname: TextView
    private lateinit var textEmail: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_authorised, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textNickname = view.findViewById(R.id.profile_nickname)
        textEmail = view.findViewById(R.id.profile_email)

        childFragmentManager.beginTransaction()
            .replace(R.id.profile_settingsContainer, FragmentSettings())
            .commit()

        buttonSignOut = view.findViewById(R.id.profile_buttonLogOut)
        buttonSignOut.setOnClickListener(){
            viewModel.signOut()
            findNavController().navigate(R.id.fragmentProfile)
        }

        viewModel.nickname.observe(viewLifecycleOwner) { text ->
            textNickname.text = text
        }

        viewModel.email.observe(viewLifecycleOwner) { text ->
            textEmail.text = text
        }

        viewModel.toastResId.observe(viewLifecycleOwner) { toastMsg ->
            toastMsg?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.toastMessageShown()
            }
        }

        viewModel.loadUserData()

    }
}