package com.example.samsung_coursework.ui.view_model

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samsung_coursework.data.FirebaseRepositoryImp
import com.example.samsung_coursework.domain.use_cases.firebase.SignUpUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {
    /** TODO внедрить через Hilt **/
    private val repository = FirebaseRepositoryImp()
    private val signUpUseCase = SignUpUseCase(repository)

    private val _isAuthorised = MutableLiveData <Boolean>(false)
    private val _toast = MutableLiveData <String>()

    val isAuthorised: LiveData<Boolean> = _isAuthorised
    val toast: LiveData<String> = _toast

    fun singUp(email: String, password: String){
        viewModelScope.launch {
            val result = signUpUseCase.signUp(email, password)
            _toast.value = result
        }
    }

}