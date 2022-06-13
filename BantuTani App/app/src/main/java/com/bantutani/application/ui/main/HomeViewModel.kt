package com.bantutani.application.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bantutani.application.data.entitiy.user.DataLogin
import com.bantutani.application.data.preference.SessionPref
import com.bantutani.application.data.repository.UserRepository


class HomeViewModel(context: Context): ViewModel(){
    private val repo = UserRepository(SessionPref(context))
    private val _data = MutableLiveData<DataLogin>()
    val data: LiveData<DataLogin> get() = _data

    fun getUser() {
        repo.userGetSession(){
            _data.postValue(it )
        }
    }

    fun fetchData() = data

}

class HomeFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
