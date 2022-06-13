package com.bantutani.application.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bantutani.application.data.entitiy.user.DataLogin
import com.bantutani.application.data.preference.SessionPref
import com.bantutani.application.data.repository.UserRepository

class LoginViewModel(context: Context): ViewModel() {
    private val repo = UserRepository(SessionPref(context))
    private val _data = MutableLiveData<DataLogin>()
    val data: LiveData<DataLogin> get() = _data
    private val _datasession = MutableLiveData<DataLogin>()
    val datasession: LiveData<DataLogin> get() = _datasession

//    Return dataclass dari entity/user/DataLogin
    fun loginUser(username: String, password: String){
        repo.userSignIn(username, password){ responsedata, error ->
            responsedata?.let {
                _data.postValue(it)
            }
        }
    }

    fun saveSession(id: Int, fullname: String, nama: String, email: String, image: String, status: String, token: String){
        repo.userSaveSession(id, fullname, nama, email, image, status, token)
    }

    fun getSession(){
        repo.userGetSession {
            _datasession.postValue(it)
        }
    }
    fun fetchSession() = _datasession
    fun fetchData() = data
}

class LoginFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}