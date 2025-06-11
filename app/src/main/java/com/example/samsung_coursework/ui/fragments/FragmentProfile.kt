package com.example.samsung_coursework.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.samsung_coursework.R
import com.example.samsung_coursework.ui.view_model.ProfileViewModel


class FragmentProfile : Fragment() {
    private val viewModel: ProfileViewModel by activityViewModels()
    private lateinit var buttonSingIn: Button
    private lateinit var buttonSignUp: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(viewModel.isAuthorised.value == true){
            findNavController().navigate(R.id.action_fragmentProfile_to_fragmentProfileAuthorised)
            return null
        }
        else{
            return inflater.inflate(R.layout.fragment_profile_unauthorized, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSingIn = view.findViewById(R.id.profile_buttonSingIn)
        buttonSignUp = view.findViewById(R.id.profile_buttonSingUp)

        viewModel.isAuthorised.observe(viewLifecycleOwner, Observer{
                isAuthorised -> if(isAuthorised) {
                findNavController().navigate(R.id.action_fragmentProfile_to_fragmentProfileAuthorised)
            }
        })

        viewModel.toast.observe(viewLifecycleOwner, Observer{
            toast -> Toast.makeText(requireContext(), toast, Toast.LENGTH_SHORT).show()
        })

        val testEmail = "testMail@aboba.ru"
        val testPassword = "testPassword123"
        buttonSignUp.setOnClickListener(){
            viewModel.signUp(testEmail, testPassword)
        }

        buttonSingIn.setOnClickListener(){
            viewModel.signIn(testEmail, testPassword)
        }



    }

}