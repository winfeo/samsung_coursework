package com.example.samsung_coursework.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samsung_coursework.data.FirebaseRepositoryImp
import com.example.samsung_coursework.domain.use_cases.firebase.SignInUseCase
import com.example.samsung_coursework.domain.use_cases.firebase.SignOutUseCase
import com.example.samsung_coursework.domain.use_cases.firebase.SignUpUseCase
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {
    /** TODO внедрить через Hilt **/
    private val repository = FirebaseRepositoryImp()
    private val signUpUseCase = SignUpUseCase(repository)
    private val signInUseCase = SignInUseCase(repository)
    private val signOutUseCase = SignOutUseCase(repository)

    private val _isAuthorised = MutableLiveData <Boolean>(false)
    private val _toast = MutableLiveData <String>()

    val isAuthorised: LiveData<Boolean> = _isAuthorised
    val toast: LiveData<String> = _toast

    fun signUp(nickname: String, email: String, password: String){
        viewModelScope.launch {
            val result = signUpUseCase.signUp(nickname, email, password)
            toastMessage(result)
        }
    }

    fun signIn(email: String, password: String){
        viewModelScope.launch {
            val (result, toast) = signInUseCase.signIn(email, password)
            if(result){
                _isAuthorised.value = true
            }
            toastMessage(toast)
        }
    }

    fun toastMessage(toast: String){
        _toast.value = toast
    }

    fun signOut(){
        viewModelScope.launch{
            signOutUseCase.signOut()
            _isAuthorised.value = false
        }
    }

}