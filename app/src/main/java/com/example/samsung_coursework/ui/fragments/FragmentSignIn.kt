package com.example.samsung_coursework.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.samsung_coursework.R
import com.example.samsung_coursework.ui.view_model.ProfileViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class FragmentSignIn : Fragment() {
    private val viewModel: ProfileViewModel by activityViewModels()
    private lateinit var buttonSignIn: Button
    private lateinit var navController: NavController
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        bottomNav.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSignIn = view.findViewById(R.id.profile_signInButtonSignIn)
        emailText = view.findViewById(R.id.profile_signInEnterEmail)
        passwordText = view.findViewById(R.id.profile_signInEnterPassword)
        navController = findNavController()

        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.profile_signInToolbar)
        toolbar.setNavigationOnClickListener(){
            navController.navigateUp()
        }

        buttonSignIn.setOnClickListener {
            val email = emailText.text.toString()
            val password = passwordText.text.toString()

            if (email.isEmpty()) {
                viewModel.toastMessage(R.string.profile_signInEmailError)
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                viewModel.toastMessage(R.string.profile_signInPasswordError)
                return@setOnClickListener
            }

            viewModel.signIn(email, password)
        }

        viewModel.isAuthorised.observe(viewLifecycleOwner, Observer{ isAuthorised ->
            if (isAuthorised) {
                navController.popBackStack()
            }
        })

        viewModel.toastResId.observe(viewLifecycleOwner) { toast ->
            toast?.let {
                Toast.makeText(requireContext(), toast, Toast.LENGTH_SHORT).show()
                viewModel.toastMessageShown()
            }
        }


    }
}