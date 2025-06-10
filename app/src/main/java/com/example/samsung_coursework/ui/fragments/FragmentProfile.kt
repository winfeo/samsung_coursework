package com.example.samsung_coursework.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.samsung_coursework.R
import com.example.samsung_coursework.data.FirebaseRepositoryImp
import com.example.samsung_coursework.domain.use_cases.firebase.SignUpUseCase


class FragmentProfile : Fragment() {
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


        buttonSingUp.setOnClickListener(){
            /** TODO это для теста, сделать норм ViewModel потом **/
            val repository = FirebaseRepositoryImp()
            val signUpUseCase = SignUpUseCase(repository)
            val textEmail = "textEmail@gmail.com"
            val textPassword = "testPassword"
            val result = signUpUseCase.singUp(textEmail,textPassword)
            Toast.makeText(view.context, result, Toast.LENGTH_SHORT).show()
        }



    }

}