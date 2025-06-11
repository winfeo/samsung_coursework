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
import androidx.lifecycle.lifecycleScope
import com.example.samsung_coursework.R
import com.example.samsung_coursework.data.FirebaseRepositoryImp
import com.example.samsung_coursework.domain.use_cases.firebase.SignUpUseCase
import com.example.samsung_coursework.ui.view_model.ProfileViewModel
import kotlinx.coroutines.launch


class FragmentProfile : Fragment() {
    private val viewModel: ProfileViewModel by activityViewModels()
    private lateinit var buttonSingIn: Button
    private lateinit var buttonSingUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_unauthorized, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSingIn = view.findViewById(R.id.profile_buttonSingIn)
        buttonSingUp = view.findViewById(R.id.profile_buttonSingUp)

        viewModel.toast.observe(viewLifecycleOwner, Observer{
            toast -> Toast.makeText(requireContext(), toast, Toast.LENGTH_SHORT).show()
        })

        val testEmail = "testMail@aboba.ru"
        val testPassword = "testPassword123"
        buttonSingUp.setOnClickListener(){
            viewModel.singUp(testEmail, testPassword)
        }



    }

}