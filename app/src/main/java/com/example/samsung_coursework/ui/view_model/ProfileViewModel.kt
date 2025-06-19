package com.example.samsung_coursework.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samsung_coursework.R
import com.example.samsung_coursework.data.FirebaseRepositoryImp
import com.example.samsung_coursework.domain.use_cases.firebase.*
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {
    private val repository = FirebaseRepositoryImp()
    private val signUpUseCase = SignUpUseCase(repository)
    private val signInUseCase = SignInUseCase(repository)
    private val signOutUseCase = SignOutUseCase(repository)
    private val getUserDataUseCase = GetUserDataUseCase(repository)
    private val changeNickname = ChangeNickname(repository)

    private val _isAuthorised = MutableLiveData <Boolean>(false)
    private val _toastResId = MutableLiveData<Int?>()
    private val _isSuccessful = MutableLiveData<Boolean>()
    private val _nickname = MutableLiveData<String>()
    private val _email = MutableLiveData<String>()

    val isAuthorised: LiveData<Boolean> = _isAuthorised
    val toastResId: LiveData<Int?> = _toastResId
    val isSuccessful: LiveData<Boolean> = _isSuccessful
    val nickname: LiveData<String> = _nickname
    val email: LiveData<String> = _email

    fun signUp(nickname: String, email: String, password: String){
        viewModelScope.launch {
            val (result, toast) = signUpUseCase.signUp(nickname, email, password)
            if(result){
                _isSuccessful.value = true
            }
            toastMessage(toast)
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

    fun toastMessage(toast: Int?){
        _toastResId.value = toast
    }

    fun signOut(){
        viewModelScope.launch{
            signOutUseCase.signOut()
            _isAuthorised.value = false
        }
    }

    fun resetSignUpStatus(){
        _isSuccessful.value = false
    }

    fun checkUserId(stayLoggedIn: Boolean){
        viewModelScope.launch {
            if (stayLoggedIn){
                val user = getUserDataUseCase.getUser()
                if (user != null) {
                    _isAuthorised.value = true
                }
            }
        }
    }

    fun loadUserData() {
        viewModelScope.launch {
            val user = getUserDataUseCase.getUser()
            if (user != null) {
                _nickname.value = user.userNickname
                _email.value = user.userEmail
            }
        }
    }

    fun changeNickname(newNickname: String){
        viewModelScope.launch {
            val success = changeNickname.changeNickname(newNickname)
            if (success) {
                _nickname.value = newNickname
                _toastResId.value = R.string.profile_toastNicknameGood
            } else {
                _toastResId.value = R.string.profile_toastNicknameBad
            }
        }
    }

    fun toastMessageShown() {
        _toastResId.value = null
    }

}