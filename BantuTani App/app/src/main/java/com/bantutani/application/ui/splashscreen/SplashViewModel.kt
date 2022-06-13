package com.bantutani.application.ui.splashscreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bantutani.application.data.preference.SessionPref
import com.bantutani.application.data.repository.UserRepository

class SplashViewModel(context: Context): ViewModel() {
    private val repo = UserRepository(SessionPref(context))
    private val _data = MutableLiveData<String>()
    val data: LiveData<String> get() = _data

    fun getData() {
         _data.postValue(repo.getToken())
    }

    fun fetchData() = data
}

class SplashFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}