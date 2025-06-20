package com.example.samsung_coursework.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.samsung_coursework.R
import com.example.samsung_coursework.ui.view_model.ProfileViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class FragmentSignUp : Fragment() {
    private val viewModel: ProfileViewModel by activityViewModels()
    private lateinit var navController: NavController
    private lateinit var errorText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        bottomNav.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.toastResId.observe(viewLifecycleOwner, Observer{ toast ->
            toast?.let {
                Toast.makeText(requireContext(), toast, Toast.LENGTH_SHORT).show()
                viewModel.toastMessageShown()
            }
        })

        viewModel.isSuccessful.observe(viewLifecycleOwner, Observer{ result ->
            if(result){
                navController.navigate(R.id.action_fragmentSignUp_to_fragmentProfileAuthorised)
                viewModel.resetSignUpStatus()
            }
        })

        val button = view.findViewById<Button>(R.id.profile_signUpButtonSingUp)
        val nicknameText = view.findViewById<EditText>(R.id.profile_signUpEnterNickname)
        val emailText = view.findViewById<EditText>(R.id.profile_signUpEnterEmail)
        val passwordText = view.findViewById<EditText>(R.id.profile_signUpEnterPassword)
        val passwordRepeatText = view.findViewById<EditText>(R.id.profile_signUpRepeatPassword)
        navController = findNavController()

        val toolbar = view.findViewById<Toolbar>(R.id.profile_signUpToolbar)
        toolbar.setNavigationOnClickListener(){
            navController.navigateUp()
        }

        button.setOnClickListener(){
            val nickname = nicknameText.text.toString()
            val email = emailText.text.toString()
            val password = passwordText.text.toString()
            val passwordRepeat = passwordRepeatText.text.toString()

            if (nickname.isEmpty()){
                viewModel.toastMessage(R.string.profile_signUpNicknameError)
                return@setOnClickListener
            }
            if(email.isEmpty()){
                viewModel.toastMessage(R.string.profile_signInEmailError)
                return@setOnClickListener
            }
            if(password.isEmpty()){
                viewModel.toastMessage(R.string.profile_signInPasswordError)
                return@setOnClickListener
            }

            if(password.equals(passwordRepeat)){
                viewModel.signUp(nickname, email, password)
            }
            else{
                viewModel.toastMessage(R.string.profile_signUpPasswordError)
                return@setOnClickListener
            }

        }

    }
}