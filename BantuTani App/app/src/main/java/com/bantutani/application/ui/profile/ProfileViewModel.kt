package com.bantutani.application.ui.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bantutani.application.data.entitiy.user.DataLogin
import com.bantutani.application.data.preference.SessionPref
import com.bantutani.application.data.repository.UserRepository

class ProfileViewModel(context: Context): ViewModel() {
    private val repo = UserRepository(SessionPref(context))
    private val _data = MutableLiveData<DataLogin>()
    val data: LiveData<DataLogin> get() = _data

    fun getProfileDetail(){
        repo.userGetSession(){
            _data.postValue(it)
        }
    }
    fun fetchData() = data

}
class ProfileFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
