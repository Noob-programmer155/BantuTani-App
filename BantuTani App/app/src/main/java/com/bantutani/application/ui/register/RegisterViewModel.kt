package com.bantutani.application.ui.register

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bantutani.application.data.preference.SessionPref
import com.bantutani.application.data.repository.UserRepository

class RegisterViewModel(context: Context): ViewModel()  {
    private val repo = UserRepository(SessionPref(context))
    private val _data = MutableLiveData<String>()
    val data: LiveData<String> get() = _data
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun registerUser(fullName: String,username: String, password: String, email: String){
        _isLoading.postValue(true)
        repo.userSignUp(fullName,username, password, email, "2187c0e5-feda-472e-afc6-c77ddf23e606avatar.png", "Pelanggan"){ status, message ->
            _data.postValue(message)
            _isLoading.postValue(false)
        }
    }

    fun getRegisterData() = data
}

class RegisterFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}