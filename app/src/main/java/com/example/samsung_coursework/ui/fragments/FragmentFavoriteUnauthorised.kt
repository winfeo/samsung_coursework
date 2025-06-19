package com.example.samsung_coursework.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.samsung_coursework.R
import com.example.samsung_coursework.ui.view_model.ProfileViewModel


class FragmentFavoriteUnauthorised : Fragment() {
    private val viewModel: ProfileViewModel by activityViewModels()
    private lateinit var button: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(viewModel.isAuthorised.value == true){
            findNavController().navigate(R.id.action_fragmentFavoriteUnauthorised_to_fragmentFavorite)
            return null
        }
        else{
            return inflater.inflate(R.layout.fragment_favorite_unauthorised, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button = view.findViewById(R.id.favorite_anuthorisedButton)
        button.setOnClickListener(){
            findNavController().navigate(R.id.action_fragmentFavoriteUnauthorised_to_fragmentSignIn)
        }
    }


}