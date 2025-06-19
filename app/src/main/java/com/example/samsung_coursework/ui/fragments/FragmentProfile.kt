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
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.samsung_coursework.R
import com.example.samsung_coursework.ui.view_model.ProfileViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class FragmentProfile : Fragment() {
    private val viewModel: ProfileViewModel by activityViewModels()
    private lateinit var buttonSignIn: Button
    private lateinit var buttonSignUp: Button
    private lateinit var navController: NavController

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

        buttonSignIn = view.findViewById(R.id.profile_buttonSingIn)
        buttonSignUp = view.findViewById(R.id.profile_buttonSingUp)
        navController = findNavController()

        viewModel.isAuthorised.observe(viewLifecycleOwner, Observer{
                isAuthorised -> if(isAuthorised) {
                navController.navigate(R.id.action_fragmentProfile_to_fragmentProfileAuthorised)
            }
        })

        buttonSignUp.setOnClickListener(){
            navController.navigate(R.id.action_fragmentProfile_to_fragmentSignUp)
        }

        buttonSignIn.setOnClickListener(){
            navController.navigate(R.id.action_fragmentProfile_to_fragmentSignIn)
        }

    }

    override fun onResume() {
        super.onResume()
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        bottomNav.visibility = View.VISIBLE
    }

}